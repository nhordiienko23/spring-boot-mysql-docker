package com.github.nhordiienko23.springmysql.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserDto(
        @NotBlank(message = "Username is required")
        String username,
        @Email
        @NotBlank(message = "Email is required")
        String email) {
}
