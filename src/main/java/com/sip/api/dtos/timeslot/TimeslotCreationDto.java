package com.sip.api.dtos.timeslot;

import com.sip.api.domains.availableClass.AvailableClass;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
public class TimeslotCreationDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;
    private Set<AvailableClass> availableClasses;
}
