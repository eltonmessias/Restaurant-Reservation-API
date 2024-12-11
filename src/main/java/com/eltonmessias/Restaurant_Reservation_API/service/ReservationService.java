package com.eltonmessias.Restaurant_Reservation_API.service;

import com.eltonmessias.Restaurant_Reservation_API.dto.ReservationDTO;
import com.eltonmessias.Restaurant_Reservation_API.enums.RESERVATION_STATUS;
import com.eltonmessias.Restaurant_Reservation_API.enums.TABLE_STATUS;
import com.eltonmessias.Restaurant_Reservation_API.exception.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;


    private ReservationDTO convertToDTO(Reservation reservation) {
//        if (reservation.getTable().getId() == null) {
//            throw new TableNotFoundException("Table not associated with the reservation");
//        }
//        if (reservation.getUser() == null) {
//            throw new UserNotFoundException("User not associated with the reservation");
//        }
        Long tableId = reservation.getTable().getId();
        System.out.println(tableId);

        return new ReservationDTO(
                reservation.getId(),
                tableId,
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


    private boolean checkTableAvailability(long tableId, long userId, LocalDateTime checkin, LocalDateTime checkout, int numberOfPeople) throws TableNotFoundException, UserNotFoundException {
        Tables table = tableRepository.findById(tableId)
                .orElseThrow(() -> new TableNotFoundException("Table with ID " + tableId + " does not exist"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " does not exist"));

        if (numberOfPeople > table.getCapacity()) {
            throw new TableExeption("Number of people exceeded the limit for this table");
        }

        // Verifica se há reservas conflitantes para a mesa
        List<Reservation> conflictingReservations = reservationRepository.findByTableAndCheckinDateBetween(table, checkin, checkout);
        for (Reservation reservation : conflictingReservations) {
            if (isOverlapping(reservation, checkin, checkout)) {
                return false;  // Se houver conflito, a mesa não está disponível
            }
        }

        return true;  // A mesa está disponível
    }



    private boolean isOverlapping(Reservation existingReservation, LocalDateTime checkin, LocalDateTime checkout) {
        LocalDateTime existingCheckin = existingReservation.getCheckinDate();
        LocalDateTime existingCheckout = existingReservation.getCheckoutDate();

        // Verifica se a nova reserva se sobrepõe com a existente, incluindo casos em que os horários coincidem
        return checkin.isBefore(existingCheckout) && checkout.isAfter(existingCheckin);
    }




    public ReservationDTO makeReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        if(!checkTableAvailability(reservationDTO.tableId(), reservationDTO.userId(), reservationDTO.check_in(), reservationDTO.check_out(),reservationDTO.numberOfPeople()))
            throw new TableExeption("Table is not available at this time");

        Tables table = tableRepository.findById(reservationDTO.tableId())
                .orElseThrow(() -> new TableNotFoundException("Table not found"));
        User user = userRepository.findById(reservationDTO.userId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));


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

    public List<ReservationDTO> getAllReservations() {
        try {
            List<Reservation> reservations = reservationRepository.findAll();
            return reservations.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (ReservationsException e) {
            throw new ReservationsException("Error loading reservations");
        }
    }

    public ReservationDTO updateReservation(ReservationDTO reservationDTO, long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new ObjectNotFoundException("Reservation not found"));
        Tables table = tableRepository.findById(reservationDTO.tableId()).orElseThrow(()-> new ObjectNotFoundException("Table not found"));
        User user = userRepository.findById(reservationDTO.userId()).orElseThrow(()-> new ObjectNotFoundException("User not found"));
        reservation.setTable(table);
        reservation.setUser(user);
        reservation.setCheckinDate(reservationDTO.check_in());
        reservation.setCheckoutDate(reservationDTO.check_out());
        reservation.setNumberOfPeople(reservationDTO.numberOfPeople());
        reservation.setStatus((reservationDTO.status()));
        reservationRepository.save(reservation);
        return convertToDTO(reservation);
    }

    public void deleteReservation(long reservationId) {
        try {
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new ObjectNotFoundException("Reservation not found"));
            reservationRepository.delete(reservation);
        } catch (ReservationsException e) {
            throw new ReservationsException("Error deleting reservation");
        }
    }

    public void cancelReservation(long reservationId) {
        try {
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new ObjectNotFoundException("Reservation not found"));
            Tables table = tableRepository.findById(reservation.getTable().getId()).orElseThrow(()-> new ObjectNotFoundException("Table not found"));
            reservation.setStatus(RESERVATION_STATUS.CANCELLED);
            table.setStatus(TABLE_STATUS.AVAILABLE);
            reservationRepository.save(reservation);
            tableRepository.save(table);
        } catch (ReservationsException e) {
            throw new ReservationsException("Error canceling reservation");
        }
    }


}
