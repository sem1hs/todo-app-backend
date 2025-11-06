package com.semihsahinoglu.todo_app.dto;

public record TodoRequest(
        String title,
        String description,
        Boolean completed
) {
}
