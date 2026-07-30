package com.apa.finance_tracker.mappers.auth;

import com.apa.finance_tracker.dtos.responses.LoginResponse;
import com.apa.finance_tracker.dtos.responses.RefreshTokenResponse;
import com.apa.finance_tracker.dtos.responses.RegisterResponse;
import com.apa.finance_tracker.dtos.responses.UserResponse;
import com.apa.finance_tracker.entitys.Token;
import com.apa.finance_tracker.entitys.User;

import java.util.Optional;

public class AuthMapperResponse {
    public RegisterResponse toRegisterResponse(User user) {
        return RegisterResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public LoginResponse toLoginResponse(Token token) {
        return LoginResponse.builder()
                .accessToken(token.getAccessToken())
                .build();
    }

    public RefreshTokenResponse toRefreshTokenResponse(Token token) {
        return RefreshTokenResponse.builder()
                .accessToken(token.getAccessToken())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
