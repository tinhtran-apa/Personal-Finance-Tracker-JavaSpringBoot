package com.apa.finance_tracker.controllers;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.constants.SuccessMessage;
import com.apa.finance_tracker.dtos.requests.LoginRequest;
import com.apa.finance_tracker.dtos.requests.RegisterRequest;
import com.apa.finance_tracker.dtos.responses.*;
import com.apa.finance_tracker.entitys.Token;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.exceptions.resource.BusinessException;
import com.apa.finance_tracker.mappers.auth.AuthMapperRegister;
import com.apa.finance_tracker.mappers.auth.AuthMapperLogin;
import com.apa.finance_tracker.mappers.auth.AuthMapperResponse;
import com.apa.finance_tracker.services.AuthService;
import com.apa.finance_tracker.services.JwtService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.Duration;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

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
                .sameSite("strict")
                .maxAge(Duration.ofDays(7))
                .build();
        LoginResponse response = new AuthMapperResponse().toLoginResponse(token);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success(SuccessMessage.AUTH_LOGGED_IN, response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh new access token")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@CookieValue(name="refreshToken") String refreshToken, HttpServletResponse cookieResponse) throws ParseException, JOSEException {
        try {
            Token token = authService.refreshToken(refreshToken);
            RefreshTokenResponse response = new AuthMapperResponse().toRefreshTokenResponse(token);
            return ResponseEntity.ok(ApiResponse.success(SuccessMessage.AUTH_REFRESH_TOKEN, response));
        } catch (BusinessException e) {
            if(e.getMessage().equals(ErrorMessage.REFRESH_TOKEN_INVALID) || e.getMessage().equals(ErrorMessage.REFRESH_TOKEN_EXPIRED)) {
                Cookie cookie = new Cookie("refreshToken", "");
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookieResponse.addCookie(cookie);
            }
            throw e;
        }
    }

    @GetMapping("/me")
    @Operation(summary = "Get user info")
    public ResponseEntity<ApiResponse<UserResponse>> getMe() {
        User user = authService.getMe();
        UserResponse response = new AuthMapperResponse().toUserResponse(user);
        return ResponseEntity.ok(ApiResponse.success(SuccessMessage.USER_RETRIEVED, response));
    }


}
