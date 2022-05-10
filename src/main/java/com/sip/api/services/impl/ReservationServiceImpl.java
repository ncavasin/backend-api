package com.sip.api.services.impl;

import com.sip.api.domains.reservation.Reservation;
import com.sip.api.dtos.reservation.ReservationCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.repositories.ReservationRepository;
import com.sip.api.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(String reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> new BadRequestException("Reservation not found"));
    }

    @Override
    public Reservation createReservation(ReservationCreationDto reservationCreationDto) {
        Reservation reservation = Reservation.builder()
                .attendees(reservationCreationDto.getAteendees())
                .availableClasses(reservationCreationDto.getAvailableClasses())
                .build();
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(String reservationId) {
        if(! reservationRepository.existsById(reservationId))
            throw new BadRequestException("Reservation not found");
        reservationRepository.deleteById(reservationId);
    }
}
