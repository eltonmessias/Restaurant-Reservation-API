package com.eltonmessias.Restaurant_Reservation_API.dto;

import com.eltonmessias.Restaurant_Reservation_API.enums.RESERVATION_STATUS;

public record ReservationDTO(
        long id,
        long user_id,
        long table_id,
        String date,
        RESERVATION_STATUS status

) {
}
