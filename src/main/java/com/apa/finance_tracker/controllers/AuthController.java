package com.apa.finance_tracker.controllers;

import com.apa.finance_tracker.constants.SuccessMessage;
import com.apa.finance_tracker.dtos.requests.LoginRequest;
import com.apa.finance_tracker.dtos.requests.RegisterRequest;
import com.apa.finance_tracker.dtos.responses.ApiResponse;
import com.apa.finance_tracker.dtos.responses.LoginResponse;
import com.apa.finance_tracker.dtos.responses.RegisterResponse;
import com.apa.finance_tracker.entitys.Token;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.mappers.auth.AuthMapperRegister;
import com.apa.finance_tracker.mappers.auth.AuthMapperLogin;
import com.apa.finance_tracker.mappers.auth.AuthMapperResponse;
import com.apa.finance_tracker.services.AuthService;
import com.apa.finance_tracker.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerUser(@Valid @RequestBody RegisterRequest request) {
        User user = new AuthMapperRegister().toEntityCreate(request);
        User savedUser = authService.registerUser(user);
        RegisterResponse response = new AuthMapperResponse().toRegisterResponse(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(SuccessMessage.AUTH_REGISTER, response));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest request) {
        User user = new AuthMapperLogin().toLogin(request);
        Token token = authService.loginUser(user);
        ResponseCookie cookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("none")
                .maxAge(Duration.ofDays(7))
                .build();
        LoginResponse response = new AuthMapperResponse().toLoginResponse(token);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success(SuccessMessage.AUTH_LOGGED_IN, response));
    }
}
