package com.sip.api.domains.reservation;

import com.sip.api.dtos.reservation.ReservationDto;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationConverter {
    public static List<ReservationDto> fromEntityToDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationConverter::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public static ReservationDto fromEntityToDto(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())

                .build();
    }
}
