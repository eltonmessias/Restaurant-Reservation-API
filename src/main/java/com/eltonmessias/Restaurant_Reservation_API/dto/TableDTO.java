package com.eltonmessias.Restaurant_Reservation_API.dto;

import com.eltonmessias.Restaurant_Reservation_API.enums.TABLE_STATUS;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TableDTO(
        Long id,
        @NotNull(message = "The name or number of the table is necessary")
        String name,
        @Min(value = 2, message = "The minimum capacity is 2")
        int capacity,
        TABLE_STATUS status
) { }
