package com.apa.finance_tracker.mappers.auth;

import com.apa.finance_tracker.dtos.requests.RegisterRequest;
import com.apa.finance_tracker.entitys.User;

public class UserMapperRegister {
    public User toEntity(RegisterRequest request) {
        return User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
