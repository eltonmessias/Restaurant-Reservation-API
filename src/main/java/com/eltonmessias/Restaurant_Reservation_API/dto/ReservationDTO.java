package com.eltonmessias.Restaurant_Reservation_API.dto;

import com.eltonmessias.Restaurant_Reservation_API.enums.RESERVATION_STATUS;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public record ReservationDTO(
        long id,
        long userId,
        long tableId,
        LocalDateTime check_in,
        LocalDateTime check_out,
        int numberOfPeople,
        RESERVATION_STATUS status

) { }
