package com.eltonmessias.Restaurant_Reservation_API.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotNull(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        @NotNull(message = "password is required")
        @NotBlank(message = "Password cannot be blank")
        String password
) {}
