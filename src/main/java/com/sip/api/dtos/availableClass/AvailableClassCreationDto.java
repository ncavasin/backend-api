package com.sip.api.dtos.availableClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableClassCreationDto {
    @NotNull
    private String activityId;
    @NotNull
    private String timeslotId;
}
