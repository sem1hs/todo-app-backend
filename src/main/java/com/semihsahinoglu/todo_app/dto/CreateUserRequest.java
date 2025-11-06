package com.semihsahinoglu.todo_app.dto;

import com.semihsahinoglu.todo_app.entity.Role;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record CreateUserRequest(
        @NotBlank(message = "Kullanıcı adı boş olamaz !")
        String username,

        @NotBlank(message = "Şifre boş olamaz !")
        String password,

        Set<Role> authorities
) {
}
