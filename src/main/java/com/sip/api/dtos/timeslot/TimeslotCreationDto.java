package com.sip.api.dtos.timeslot;

import com.sip.api.domains.availableClass.AvailableClass;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
public class TimeslotCreationDto {
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private DayOfWeek dayOfWeek;
    private Set<AvailableClass> availableClasses;
}
