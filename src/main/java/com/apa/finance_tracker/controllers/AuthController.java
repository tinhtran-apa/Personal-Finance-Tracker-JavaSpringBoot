package com.apa.finance_tracker.controllers;

import com.apa.finance_tracker.constants.SuccessMessage;
import com.apa.finance_tracker.dtos.requests.LoginRequest;
import com.apa.finance_tracker.dtos.requests.RegisterRequest;
import com.apa.finance_tracker.dtos.responses.ApiResponse;
import com.apa.finance_tracker.dtos.responses.LoginResponse;
import com.apa.finance_tracker.dtos.responses.UserResponse;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.mappers.auth.UserMapperRegister;
import com.apa.finance_tracker.mappers.auth.UserMapperResponse;
import com.apa.finance_tracker.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        User user = new UserMapperRegister().toEntity(request);
        User savedUser = authService.register(user);
        UserResponse response = new UserMapperResponse().toResponse(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(SuccessMessage.AUTH_REGISTER ,response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.AUTH_LOGGED_IN, response));
    }
}
