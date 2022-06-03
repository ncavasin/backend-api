package com.sip.api.domains.plan;

import com.sip.api.dtos.plan.PlanDto;

import java.util.List;
import java.util.stream.Collectors;

public class PlanConverter {

    public static List<PlanDto> fromEntityToDto(List<Plan> plans) {
        return plans.stream()
                .map(PlanConverter::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public static PlanDto fromEntityToDto(Plan plan) {
        return PlanDto.builder()
                .id(plan.getId())
                .name(plan.getName())
                .description(plan.getDescription())
                .price(plan.getPrice())
                .activitiesLimit(plan.getActivitiesLimit())
                .build();
    }

    public static Plan fromDtoToEntity(PlanDto planDto) {
        return Plan.builder()
                .name(planDto.getName())
                .description(planDto.getDescription())
                .price(planDto.getPrice())
                .activitiesLimit(planDto.getActivitiesLimit())
                .build();
    }
}
