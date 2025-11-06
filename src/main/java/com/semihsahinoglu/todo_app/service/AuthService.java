package com.semihsahinoglu.todo_app.service;

import com.semihsahinoglu.todo_app.dto.CreateUserRequest;
import com.semihsahinoglu.todo_app.dto.JwtTokenResponse;
import com.semihsahinoglu.todo_app.dto.LoginRequest;
import com.semihsahinoglu.todo_app.entity.User;
import com.semihsahinoglu.todo_app.exception.InvalidRefreshTokenException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public JwtTokenResponse loginAndGenerateToken(LoginRequest loginRequest) {
        String accessToken = null;
        String refreshToken = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

            if (authentication.isAuthenticated()) {
                accessToken = jwtService.generateAccessToken(loginRequest.username());
                refreshToken = jwtService.generateRefreshToken(loginRequest.username());
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException(String.format("Kullanıcı Bulunamadı -> %s ", loginRequest.username()));
        }

        JwtTokenResponse response = JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return response;
    }

    public JwtTokenResponse signUpAndGenerateToken(CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        JwtTokenResponse response = JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return response;
    }

    public JwtTokenResponse refreshAccessToken(String refreshToken) {

        if (!jwtService.validateToken(refreshToken)) {
            throw new InvalidRefreshTokenException("Geçersiz veya süresi dolmuş refresh token");
        }

        String username = jwtService.extractUsername(refreshToken);
        String newAccessToken = jwtService.generateAccessToken(username);

        JwtTokenResponse response = JwtTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();

        return response;
    }
}
