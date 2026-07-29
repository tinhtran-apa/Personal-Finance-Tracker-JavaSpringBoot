package com.apa.finance_tracker.mappers.auth;

import com.apa.finance_tracker.dtos.requests.RegisterRequest;
import com.apa.finance_tracker.entitys.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthMapperRegister {
    public User toEntityCreate (RegisterRequest request) {
        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .build();
    }
}
