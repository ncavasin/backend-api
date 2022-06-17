package com.sip.api.services.impl;

import com.sip.api.domains.plan.Plan;
import com.sip.api.dtos.plan.*;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.PlanRepository;
import com.sip.api.services.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;

    @Override
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    @Override
    public Plan findById(String planId) {
        return planRepository.findById(planId).orElseThrow(() -> new BadRequestException("Plan not found"));
    }

    @Override
    public Plan findMostExpensivePlanByUser(String userId) {
        return planRepository.findMostExpensivePlanByUser(userId)
                .orElseThrow(() -> new NotFoundException("User in not subscribed to any plan!"));
    }

    @Override
    public Plan createPlan(PlanCreationDto planCreationDto) {
        if (planRepository.existsByName(planCreationDto.getName()))
            throw new BadRequestException(String.format("Plan with name '%s' already exists", planCreationDto.getName()));
        checkPrice(planCreationDto.getPrice());
        checkActivitiesLimit(planCreationDto.getActivitiesLimit());
        return planRepository.save(Plan.builder()
                .name(planCreationDto.getName())
                .description(planCreationDto.getDescription())
                .price(planCreationDto.getPrice())
                .activitiesLimit(planCreationDto.getActivitiesLimit())
                .build());
    }

    @Override
    public Plan updateName(UpdatePlanNameDto updatePlanNameDto) {
        return null;
    }

    @Override
    public Plan updateDescription(UpdatePlanDescriptionDto updatePlanDescriptionDto) {
        return null;
    }

    @Override
    public Plan updatePrice(UpdatePlanPriceDto updatePlanPriceDto) {
        return null;
    }

    @Override
    public Plan updateActivitiesLimit(UpdatePlanActivitiesDto updatePlanActivitiesLimitDto) {
        return null;
    }

    @Override
    public void deletePlanById(String planId) {
        if (!planRepository.existsById(planId))
            throw new NotFoundException("Plan not found");
        planRepository.deleteById(planId);
    }

    private void checkActivitiesLimit(Integer activitiesLimit) {
        if (activitiesLimit < 0)
            throw new BadRequestException("Activities limit cannot be negative!");
    }

    private void checkPrice(Double price) {
        if (price < 0) {
            throw new BadRequestException("Price cannot be negative!");
        }
    }
}
