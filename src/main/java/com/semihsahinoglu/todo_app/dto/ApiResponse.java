package com.semihsahinoglu.todo_app.dto;

public sealed interface ApiResponse permits JwtTokenResponse, TodoResponse {
}
