package com.sip.api.repositories;

import com.sip.api.domains.plan.Plan;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, String> {

    @NonNull
    List<Plan> findAll();

    boolean existsByName(String name);
}
