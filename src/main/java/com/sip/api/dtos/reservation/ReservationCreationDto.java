package com.sip.api.dtos.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreationDto {
    @NotNull
    private String availableClassId;
    @NotNull
    private Set<String> attendeesIds;
}
