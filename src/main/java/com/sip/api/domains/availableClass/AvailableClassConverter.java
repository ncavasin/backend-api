package com.sip.api.domains.availableClass;

import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.activity.ActivityDto;
import com.sip.api.dtos.availableClass.AvailableClassDto;
import com.sip.api.dtos.timeslot.TimeslotDto;
import org.springframework.stereotype.Component;

import java.util.List;
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
                        .professor(UserConverter.entityToDtoSlim(availableClass.getActivity().getProfessor()))
                        .attendeesLimit(availableClass.getActivity().getAttendeesLimit())
                        .basePrice(availableClass.getActivity().getBasePrice())
                        .build())
                .timeslotDto(TimeslotDto.builder()
                        .id(availableClass.getTimeslot().getId())
                        .startTime(availableClass.getTimeslot().getStartTime())
                        .endTime(availableClass.getTimeslot().getEndTime())
                        .dayOfWeek(availableClass.getTimeslot().getDayOfWeek())
                        .build())
                .build();
    }
}
