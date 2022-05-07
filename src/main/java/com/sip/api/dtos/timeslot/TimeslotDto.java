package com.sip.api.dtos.timeslot;

import com.sip.api.domains.availableClass.AvailableClass;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
public class TimeslotDto {
    private String id;
    private LocalTime startTime;
    private LocalTime endTime;
}
