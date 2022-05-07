package com.sip.api.domains.availableClass;

import com.sip.api.dtos.availableClass.AvailableClassDto;
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
                .activity(availableClass.getActivity())
                .timeslot(availableClass.getTimeslot())
                .build();
    }
}
