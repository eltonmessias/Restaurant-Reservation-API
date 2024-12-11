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

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable long id, @RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(reservationService.updateReservation(reservationDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable long id) {
        reservationService.cancelReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
