package com.sip.api.controllers;

import com.sip.api.domains.plan.PlanConverter;
import com.sip.api.dtos.plan.*;
import com.sip.api.services.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanController {
    private final PlanService planService;

    @GetMapping("/all")
    public List<PlanDto> getAllPlans() {
        return PlanConverter.fromEntityToDto(planService.getAllPlans());
    }

    @GetMapping("/{planId}")
    public PlanDto findById(@PathVariable("planId") String planId) {
        return PlanConverter.fromEntityToDto(planService.findById(planId));
    }

    @PostMapping
    public PlanDto createPlan(@RequestBody PlanCreationDto planCreationDto) {
        return PlanConverter.fromEntityToDto(planService.createPlan(planCreationDto));
    }

    @PutMapping("/name")
    public PlanDto updatePlanName(@RequestBody @Validated UpdatePlanNameDto updatePlanNameDto) {
        return PlanConverter.fromEntityToDto(planService.updateName(updatePlanNameDto));
    }

    @PutMapping("/description")
    public PlanDto updatePlanDescription(@RequestBody @Validated UpdatePlanDescriptionDto updatePlanDescriptionDto) {
        return PlanConverter.fromEntityToDto(planService.updateDescription(updatePlanDescriptionDto));
    }

    @PutMapping("/price")
    public PlanDto updatePlanPrice(@RequestBody @Validated UpdatePlanPriceDto updatePlanPriceDto) {
        return PlanConverter.fromEntityToDto(planService.updatePrice(updatePlanPriceDto));
    }

    @PutMapping("/activities-limit")
    public PlanDto updatePlanActivitiesLimit(@RequestBody @Validated UpdatePlanActivitiesDto updatePlanActivitiesLimitDto) {
        return PlanConverter.fromEntityToDto(planService.updateActivitiesLimit(updatePlanActivitiesLimitDto));
    }

    @DeleteMapping("/{planId}")
    public void deletePlanById(@PathVariable("planId") String planId) {
        planService.deletePlanById(planId);
    }
}
