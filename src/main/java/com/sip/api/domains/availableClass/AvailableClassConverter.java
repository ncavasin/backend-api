package com.sip.api.domains.availableClass;

import com.sip.api.domains.user.User;
import com.sip.api.dtos.activity.ActivityDto;
import com.sip.api.dtos.availableClass.AvailableClassDto;
import com.sip.api.dtos.timeslot.TimeslotDto;
import com.sip.api.dtos.user.UserSlimDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AvailableClassConverter {

    public static List<AvailableClassDto> fromEntityToDto(List<AvailableClass> availableClasses) {
        return availableClasses.stream()
                .map(AvailableClassConverter::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public static AvailableClassDto fromEntityToDto(AvailableClass availableClass) {
        return AvailableClassDto.builder()
                .id(availableClass.getId())
                .activityDto(ActivityDto.builder()
                        .id(availableClass.getActivity().getId())
                        .name(availableClass.getActivity().getName())
                        .atendeesLimit(availableClass.getActivity().getAtendeesLimit())
                        .basePrice(availableClass.getActivity().getBasePrice())
                        .build())
                .timeslotDto(TimeslotDto.builder()
                        .id(availableClass.getTimeslot().getId())
                        .startTime(availableClass.getTimeslot().getStartTime())
                        .endTime(availableClass.getTimeslot().getEndTime())
                        .dayOfWeek(availableClass.getTimeslot().getDayOfWeek())
                        .build())
                .attendees(mapToSlimUserDtoSet(availableClass.getAttendees()))
                .rejectedAttendees(mapToSlimUserDtoSet(availableClass.getRejectedAttendees()))
                .build();
    }

    private static Set<UserSlimDto> mapToSlimUserDtoSet(Set<User> attendees) {
        return attendees.stream()
                .map(AvailableClassConverter::mapToSlimUserDto)
                .collect(Collectors.toSet());
    }

    private static UserSlimDto mapToSlimUserDto(User attendee) {
        return UserSlimDto.builder()
                .id(attendee.getId())
                .firstName(attendee.getFirstName())
                .lastName(attendee.getLastName())
                .email(attendee.getEmail())
                .build();
    }
}
