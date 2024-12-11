package com.eltonmessias.Restaurant_Reservation_API.controller;

import com.eltonmessias.Restaurant_Reservation_API.dto.ReservationDTO;
import com.eltonmessias.Restaurant_Reservation_API.exception.TableNotFoundException;
import com.eltonmessias.Restaurant_Reservation_API.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) throws TableNotFoundException {
        return new ResponseEntity<>(reservationService.makeReservation(reservationDTO), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationDTO>> getReservations() {
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }
}
