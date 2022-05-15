package com.sip.api.services.impl;

import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.domains.enums.UserStatus;
import com.sip.api.domains.reservation.Reservation;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.reservation.ReservationCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.repositories.ReservationRepository;
import com.sip.api.services.AvailableClassService;
import com.sip.api.services.ReservationService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final AvailableClassService availableClassService;
    private final UserService userService;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(String reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> new BadRequestException("Reservation not found"));
    }

    @Override
    public Reservation addUserToReservation(ReservationCreationDto reservationCreationDto) {
        AvailableClass availableClass = availableClassService.findById(reservationCreationDto.getAvailableClassId());
        User attendee = userService.findById(reservationCreationDto.getAttendeeId());

        // Find reservation by availableClassId
        Reservation reservation = reservationRepository
                .findByAvailableClass_Id(availableClass.getId())
                // If there is no reservation for this activity, create a new one with empty attendees
                .orElse(Reservation.builder()
                        .availableClass(availableClass)
                        .attendees(new HashSet<>())
                        .build());

        checkUserIsAbleToMakeReservations(attendee);

        // Add attendee to reservation or throw exception if attendee is already in the reservation
        reservation.addAttendee(attendee);
        return reservationRepository.save(reservation);
    }

    private void checkUserIsAbleToMakeReservations(User attendee) {
        if (attendee.getStatus().equals(UserStatus.INACTIVE))
            throw new BadRequestException("Attendee must activate account before making reservations");
        if (attendee.getStatus().equals(UserStatus.DEACTIVATED))
            throw new BadRequestException("Attendee account has been deactivated");
        if (attendee.getStatus().equals(UserStatus.OVERDUE))
            throw new BadRequestException("Attendee account payment is overdue");
    }

    @Override
    public Reservation removeUserFromReservationUsingAvailableClassId(String availableClassId, String attendeeId) {
        AvailableClass availableClass = availableClassService.findById(availableClassId);
        Reservation reservation = reservationRepository
                .findByAvailableClass_Id(availableClass.getId())
                .orElseThrow(() -> new BadRequestException(String.format("Can't remove user since there's no Reservation related to AvailableClass '%s'.", availableClassId)));
        reservation.removeAttendee(userService.findById(attendeeId));
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(String reservationId) {
        if (!reservationRepository.existsById(reservationId))
            throw new BadRequestException("Reservation not found");
        reservationRepository.deleteById(reservationId);
    }
}
