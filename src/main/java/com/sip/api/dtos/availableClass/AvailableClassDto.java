package com.sip.api.dtos.availableClass;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.activity.ActivityDto;
import com.sip.api.dtos.timeslot.TimeslotDto;
import com.sip.api.dtos.user.UserSlimDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

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
    private Set<UserSlimDto> attendees;
    private Set<UserSlimDto> rejectedAttendees;
}
