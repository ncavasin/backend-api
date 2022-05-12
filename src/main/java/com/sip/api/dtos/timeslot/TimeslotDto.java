package com.sip.api.dtos.timeslot;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
public class TimeslotDto {
    private String id;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private DayOfWeek dayOfWeek;
}
