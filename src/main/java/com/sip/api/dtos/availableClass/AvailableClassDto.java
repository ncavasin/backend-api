package com.sip.api.dtos.availableClass;

import com.sip.api.dtos.activity.ActivityDto;
import com.sip.api.dtos.timeslot.TimeslotDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableClassDto {
    @NotNull
    private String id;
    @NotNull
    private ActivityDto activityDto;
    @NotNull
    private TimeslotDto timeslotDto;
}
