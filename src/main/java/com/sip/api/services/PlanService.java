package com.sip.api.services;

import com.sip.api.domains.plan.Plan;
import com.sip.api.dtos.plan.*;

import java.util.List;

public interface PlanService {

    List<Plan> getAllPlans();

    Plan findById(String planId);

    Plan findMostExpensivePlanByUser(String userId);

    Plan createPlan(PlanCreationDto planCreationDto);

    Plan updateName(UpdatePlanNameDto updatePlanNameDto);

    Plan updateDescription(UpdatePlanDescriptionDto updatePlanDescriptionDto);

    Plan updatePrice(UpdatePlanPriceDto updatePlanPriceDto);

    Plan updateActivitiesLimit(UpdatePlanActivitiesDto updatePlanActivitiesLimitDto);

    void deletePlanById(String planId);
}
