package com.eltonmessias.Restaurant_Reservation_API.repository;

import com.eltonmessias.Restaurant_Reservation_API.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
