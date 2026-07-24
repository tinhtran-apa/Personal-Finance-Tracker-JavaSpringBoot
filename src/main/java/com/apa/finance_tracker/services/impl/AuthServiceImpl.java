package com.apa.finance_tracker.services.impl;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.dtos.responses.LoginResponse;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.enums.RoleType;
import com.apa.finance_tracker.exceptions.resource.DuplicateResourceException;
import com.apa.finance_tracker.repositories.UserRepository;
import com.apa.finance_tracker.security.JwtService;
import com.apa.finance_tracker.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException(ErrorMessage.USER_ALREADY_EXISTS);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleType.USER);
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.genrateToken(userDetails);
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

}
