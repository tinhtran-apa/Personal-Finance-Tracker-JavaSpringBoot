package com.apa.finance_tracker.mappers.user;

import com.apa.finance_tracker.dtos.requests.LoginRequest;
import com.apa.finance_tracker.entitys.User;

public class UserMapperLogin {
    public User toLogin (LoginRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
