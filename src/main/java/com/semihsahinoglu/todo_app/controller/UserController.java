package com.semihsahinoglu.todo_app.controller;

import com.semihsahinoglu.todo_app.dto.ApiResponse;
import com.semihsahinoglu.todo_app.dto.UserResponse;
import com.semihsahinoglu.todo_app.security.CustomUserDetails;
import com.semihsahinoglu.todo_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getCurrentUser(CustomUserDetails userDetails) {
        UserResponse userResponse = userService.getCurrentUser(userDetails);
        return ResponseEntity.ok().body(userResponse);
    }
}
