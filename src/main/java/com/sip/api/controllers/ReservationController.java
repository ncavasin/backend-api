package com.sip.api.controllers;

import com.sip.api.domains.reservation.ReservationConverter;
import com.sip.api.dtos.availableClass.AvailableClassAttendeeAmountDto;
import com.sip.api.dtos.reservation.ReservationCreationDto;
import com.sip.api.dtos.reservation.ReservationDto;
import com.sip.api.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/all")
    public List<ReservationDto> findAll() {
        return ReservationConverter.fromEntityToDto(reservationService.findAll());
    }

    @GetMapping("/{reservationId}")
    public ReservationDto findById(@PathVariable("reservationId") String reservationId) {
        return ReservationConverter.fromEntityToDto(reservationService.findById(reservationId));
    }

    @GetMapping("/attendee-amount-by-available-class/{availableClassId}")
    public AvailableClassAttendeeAmountDto findFreeSlots(@PathVariable("availableClassId") String availableClassId) {
        return ReservationConverter.fromEntityToDto(reservationService.countAttendeeAmountByAvailableClassId(availableClassId));
    }

    @PostMapping()
    public ReservationDto addUserToReservation(@RequestBody ReservationCreationDto reservationCreationDto) {
        return ReservationConverter.fromEntityToDto(reservationService.addUserToReservation(reservationCreationDto));
    }

    @PutMapping("/remove-user/{attendeeId}/from-available-class/{availableClassId}")
    public ReservationDto removeUserFromReservation(@PathVariable("attendeeId") String attendeeId, @PathVariable("availableClassId") String availableClassId) {
        return ReservationConverter.fromEntityToDto(reservationService.removeUserFromReservationUsingAvailableClassId(attendeeId, availableClassId));
    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable("reservationId") String reservationId) {
        reservationService.deleteReservation(reservationId);
    }
}
