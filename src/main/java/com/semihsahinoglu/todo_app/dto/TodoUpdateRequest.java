package com.semihsahinoglu.todo_app.dto;

import java.util.Optional;

public record TodoUpdateRequest(
        Optional<String> title,
        Optional<String> description,
        Optional<Boolean> completed
) {
}
