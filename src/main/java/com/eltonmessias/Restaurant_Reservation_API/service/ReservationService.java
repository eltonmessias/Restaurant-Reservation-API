package com.eltonmessias.Restaurant_Reservation_API.service;

import com.eltonmessias.Restaurant_Reservation_API.dto.ReservationDTO;
import com.eltonmessias.Restaurant_Reservation_API.enums.RESERVATION_STATUS;
import com.eltonmessias.Restaurant_Reservation_API.exception.TableExeption;
import com.eltonmessias.Restaurant_Reservation_API.exception.TableNotFoundException;
import com.eltonmessias.Restaurant_Reservation_API.exception.UserNotFoundException;
import com.eltonmessias.Restaurant_Reservation_API.model.Reservation;
import com.eltonmessias.Restaurant_Reservation_API.model.Tables;
import com.eltonmessias.Restaurant_Reservation_API.model.User;
import com.eltonmessias.Restaurant_Reservation_API.repository.ReservationRepository;
import com.eltonmessias.Restaurant_Reservation_API.repository.TableRepository;
import com.eltonmessias.Restaurant_Reservation_API.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;


    private ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getTable().getId(),
                reservation.getUser().getId(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getNumberOfPeople(),
                reservation.getStatus()
        );
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDTO, reservation);
        return reservation;
    }
    private boolean checkTableAvailability(long tableId, long userId, LocalDateTime checkin, LocalDateTime checkout, int numberOfPeople) throws TableNotFoundException {
        Tables table = tableRepository.findById(tableId).orElseThrow(()-> new TableNotFoundException("Table does not exists"));

        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User does not exists"));

        if(numberOfPeople > table.getCapacity()){
            throw new TableExeption("Number of people exceeded the limit");
        }


        List<Reservation> conflictingReservations = reservationRepository.findByTableAndCheckinDateBetween(table, checkin, checkout);

        for(Reservation reservation : conflictingReservations){
            if(isOverlapping(reservation, checkin, checkout)){
                return false;
            }
        }



        return true;
    }

    private boolean isOverlapping(Reservation existingReservation, LocalDateTime checkin, LocalDateTime checkout){
        LocalDateTime existingCheckin = existingReservation.getCheckinDate();
        LocalDateTime existingCheckout = existingReservation.getCheckoutDate();

        return checkin.isBefore(existingCheckin) && checkout.isAfter(existingCheckout);
    }

    public ReservationDTO makeReservation(long tableId, long userId, LocalDateTime checkin, LocalDateTime checkout, int numberOfPeople, RESERVATION_STATUS status) throws TableNotFoundException {
        Reservation reservation = new Reservation();
        if(!checkTableAvailability(tableId, userId, checkin, checkout, numberOfPeople))
            throw new TableExeption("Table is not available");

        reservation.setCheckinDate(checkin);
        reservation.setCheckoutDate(checkout);
        reservation.setTable(tableRepository.findById(tableId).get());
        reservation.setNumberOfPeople(numberOfPeople);
        reservation.setUser(userRepository.findById(userId).get());
        reservation.setStatus(status);
        reservationRepository.save(reservation);
        return convertToDTO(reservation);
    }


}
