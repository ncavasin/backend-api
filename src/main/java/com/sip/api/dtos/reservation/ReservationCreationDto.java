package com.sip.api.dtos.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreationDto {
    @NotNull
    private String availableClassId;
    @NotNull
    private String attendeeId;
}
