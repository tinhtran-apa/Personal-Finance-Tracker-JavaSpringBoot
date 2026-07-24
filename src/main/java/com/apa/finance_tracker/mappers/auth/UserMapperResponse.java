package com.apa.finance_tracker.mappers.auth;

import com.apa.finance_tracker.dtos.responses.UserResponse;
import com.apa.finance_tracker.entitys.User;

public class UserMapperResponse {
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
