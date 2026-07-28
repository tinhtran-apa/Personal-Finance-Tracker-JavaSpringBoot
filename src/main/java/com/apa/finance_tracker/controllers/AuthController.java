package com.apa.finance_tracker.controllers;

import com.apa.finance_tracker.constants.SuccessMessage;
import com.apa.finance_tracker.dtos.requests.LoginRequest;
import com.apa.finance_tracker.dtos.requests.RegisterRequest;
import com.apa.finance_tracker.dtos.responses.ApiResponse;
import com.apa.finance_tracker.dtos.responses.LoginResponse;
import com.apa.finance_tracker.dtos.responses.UserResponse;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.mappers.user.UserMapperCreate;
import com.apa.finance_tracker.mappers.user.UserMapperLogin;
import com.apa.finance_tracker.mappers.user.UserMapperResponse;
import com.apa.finance_tracker.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody RegisterRequest request) {
        User user = new UserMapperCreate().toEntityCreate(request);
        User savedUser = authService.registerUser(user);
        UserResponse response = new UserMapperResponse().toResponse(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(SuccessMessage.AUTH_REGISTER, response));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest request) {
        User user = new UserMapperLogin().toLogin(request);
        authService.loginUser(user);

        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.AUTH_LOGGED_IN, LoginResponse.builder()
                .accessToken("123")
                .refreshToken("321")
                .build()));
    }
}
