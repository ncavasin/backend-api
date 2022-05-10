package com.sip.api.services.impl;

import com.sip.api.domains.reservation.Reservation;
import com.sip.api.dtos.reservation.ReservationCreationDto;
import com.sip.api.services.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationServiceImpl implements ReservationService {
    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public Reservation findById(String reservationId) {
        return null;
    }

    @Override
    public Reservation createReservation(ReservationCreationDto reservationCreationDto) {
        return null;
    }

    @Override
    public void deleteReservation(String reservationId) {

    }
}
