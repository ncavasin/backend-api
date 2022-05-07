package com.sip.api.dtos.availableClass;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.timeslot.Timeslot;
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
    private Activity activity;
    @NotNull
    private Timeslot timeslot;
}
