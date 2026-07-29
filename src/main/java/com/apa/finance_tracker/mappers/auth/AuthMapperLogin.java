package com.apa.finance_tracker.mappers.auth;

import com.apa.finance_tracker.dtos.requests.LoginRequest;
import com.apa.finance_tracker.entitys.User;

public class AuthMapperLogin {
    public User toLogin (LoginRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
