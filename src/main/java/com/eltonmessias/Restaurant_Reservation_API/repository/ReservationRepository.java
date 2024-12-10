package com.eltonmessias.Restaurant_Reservation_API.repository;

import com.eltonmessias.Restaurant_Reservation_API.model.Reservation;
import com.eltonmessias.Restaurant_Reservation_API.model.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByTableAndCheckinDateBetween(Tables tableId, LocalDateTime from, LocalDateTime to);
}
