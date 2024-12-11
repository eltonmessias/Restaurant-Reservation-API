package com.eltonmessias.Restaurant_Reservation_API.service;

import com.eltonmessias.Restaurant_Reservation_API.dto.ReservationDTO;
import com.eltonmessias.Restaurant_Reservation_API.enums.RESERVATION_STATUS;
import com.eltonmessias.Restaurant_Reservation_API.enums.TABLE_STATUS;
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
        System.out.println("Fora da hora");
        return true;
    }

    private boolean isOverlapping(Reservation existingReservation, LocalDateTime checkin, LocalDateTime checkout) {
        LocalDateTime existingCheckin = existingReservation.getCheckinDate();
        LocalDateTime existingCheckout = existingReservation.getCheckoutDate();

        // Check if the new reservation overlaps with the existing reservation
        return checkin.isBefore(existingCheckout) && checkout.isAfter(existingCheckin);
    }

    public ReservationDTO makeReservation(ReservationDTO reservationDTO) throws TableNotFoundException {
        Reservation reservation = new Reservation();

        if(!checkTableAvailability(reservationDTO.tableId(), reservationDTO.userId(), reservationDTO.check_in(), reservationDTO.check_out(),reservationDTO.numberOfPeople()))
            throw new TableExeption("Table is not available at this time");

        Tables table = tableRepository.findById(reservationDTO.tableId()).get();
        User user = userRepository.findById(reservationDTO.userId()).get();

        reservation.setCheckinDate(reservationDTO.check_in());
        reservation.setCheckoutDate(reservationDTO.check_out());
        reservation.setTable(table);
        reservation.setNumberOfPeople(reservationDTO.numberOfPeople());
        reservation.setUser(user);
        reservation.setStatus((reservationDTO.status()));
        reservationRepository.save(reservation);
        table.setStatus(TABLE_STATUS.RESERVED);
        return convertToDTO(reservation);
    }


}
