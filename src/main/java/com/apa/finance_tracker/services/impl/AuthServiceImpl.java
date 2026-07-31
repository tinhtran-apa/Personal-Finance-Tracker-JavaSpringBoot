package com.apa.finance_tracker.services.impl;


import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.entitys.Token;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.exceptions.resource.UnauthorizedException;
import com.apa.finance_tracker.helpers.SecurityHelper;
import com.apa.finance_tracker.repositories.TokenRepository;
import com.apa.finance_tracker.repositories.UserRepository;
import com.apa.finance_tracker.services.AuthService;
import com.apa.finance_tracker.services.JwtService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Value("${jwt.refresh-expiration}")
    private Integer refreshExpiration;

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final SecurityHelper securityHelper;

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceNotFoundException(ErrorMessage.USER_ALREADY_EXISTS);
        }
        return userRepository.save(user);
    }

    @Override
    public Token loginUser(User user) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            User principal = (User) authenticate.getPrincipal();
            String accessToken = jwtService.generateAccessToken(principal.getId());
            String refeshToken = jwtService.generateRefreshToken(principal.getId());
            Token token = Token.builder()
                    .accessToken(accessToken)
                    .refreshToken(refeshToken)
                    .expiresAt(LocalDateTime.now().plusDays(refreshExpiration))
                    .user(principal)
                    .build();
            tokenRepository.save(token);
            return token;
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(ErrorMessage.USER_UNAUTHORIZED);
        }
    }

    @Override
    public Token refreshToken(String refreshToken) throws ParseException, JOSEException {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BusinessException(ErrorMessage.REFRESH_TOKEN_MISSING);
        }
        if (!jwtService.verifyRefreshToken(refreshToken)) {
            throw new BusinessException(ErrorMessage.REFRESH_TOKEN_INVALID);
        }
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BusinessException(ErrorMessage.REFRESH_TOKEN_INVALID));
        if (token.isRevoke()) {
            throw new BusinessException(ErrorMessage.REFRESH_TOKEN_REVOKED);
        }
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(token);
            throw new BusinessException(ErrorMessage.REFRESH_TOKEN_EXPIRED);
        }
        Long userID = token.getUser().getId();
        String newAccessToken = jwtService.generateAccessToken(userID);
        token.setAccessToken(newAccessToken);
        tokenRepository.save(token);
        return token;
    }

    @Override
    public User getCurrentUser() {
        return securityHelper.getCurrentUser();
    }

    @Override
    public void logOut(String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new BusinessException(ErrorMessage.REFRESH_TOKEN_MISSING));
        tokenRepository.delete(token);
    }
}
