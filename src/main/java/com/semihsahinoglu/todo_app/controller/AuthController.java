package com.semihsahinoglu.todo_app.controller;

import com.semihsahinoglu.todo_app.dto.CreateUserRequest;
import com.semihsahinoglu.todo_app.dto.JwtTokenResponse;
import com.semihsahinoglu.todo_app.dto.LoginRequest;
import com.semihsahinoglu.todo_app.dto.RefreshTokenRequest;
import com.semihsahinoglu.todo_app.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtTokenResponse token = authService.loginAndGenerateToken(loginRequest);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtTokenResponse> signUp(@Valid @RequestBody CreateUserRequest createUserRequest) {
        JwtTokenResponse token = authService.signUpAndGenerateToken(createUserRequest);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtTokenResponse> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest request) {
        JwtTokenResponse token = authService.refreshAccessToken(request.refreshToken());
        return ResponseEntity.ok().body(token);
    }

}
