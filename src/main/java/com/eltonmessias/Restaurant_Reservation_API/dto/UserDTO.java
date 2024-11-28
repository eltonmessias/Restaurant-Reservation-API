package com.eltonmessias.Restaurant_Reservation_API.dto;


import com.eltonmessias.Restaurant_Reservation_API.enums.ROLE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public record UserDTO(
        @NotNull(message = "The name is necessary")
                @NotBlank(message = "The name cannot be blank")
        String name,
        @NotNull
                @Email(message = "Enter a valid email")
        String email,
        @NotNull(message = "Password is required")
                @NotBlank(message = "Password cannot be blank")
        String password,
        @NotNull(message = "Role is required")
        ROLE role
) { }
