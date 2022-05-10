package com.sip.api.dtos.reservation;

import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.domains.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreationDto {
    private Set<AvailableClass> availableClasses;
    private Set<User> ateendees;
}
