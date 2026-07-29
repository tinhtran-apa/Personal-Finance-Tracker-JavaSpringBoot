package com.apa.finance_tracker.services.impl;


import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.entitys.Token;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.repositories.TokenRepository;
import com.apa.finance_tracker.repositories.UserRepository;
import com.apa.finance_tracker.services.AuthService;
import com.apa.finance_tracker.services.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Value("${jwt.refresh-expiration}")
    private Integer refreshExpiration;

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public User registerUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceNotFoundException(ErrorMessage.USER_ALREADY_EXISTS);
        }
        return userRepository.save(user);
    }

    @Override
    public Token loginUser(User user) {
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
    }
}
