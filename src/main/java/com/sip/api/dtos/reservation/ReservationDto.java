package com.sip.api.dtos.reservation;

import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.dtos.user.UserSlimDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private String id;
    private AvailableClass availableClass;
    private Set<UserSlimDto> attendees;
}
