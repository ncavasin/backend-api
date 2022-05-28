package com.sip.api.services;

import com.sip.api.domains.reservation.Reservation;
import com.sip.api.dtos.reservation.ReservationCreationDto;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();

    Reservation findById(String reservationId);

    Integer countAttendeeAmountByAvailableClassId(String availableClassId);

    Reservation addUserToReservation(ReservationCreationDto reservationCreationDto);

    Reservation removeUserFromReservationUsingAvailableClassId(String attendeeId, String availableClassId);

    void deleteReservation(String reservationId);
}
