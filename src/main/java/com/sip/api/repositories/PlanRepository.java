package com.sip.api.repositories;

import com.sip.api.domains.plan.Plan;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, String> {

    @NonNull
    List<Plan> findAll();

    @Query("SELECT s.plan FROM Subscription s WHERE s.user.id = :userId")
    List<Plan> findAllByUserId(String userId);

    @Query("SELECT p FROM Plan p JOIN Subscription s ON p.id = s.plan.id " +
            "WHERE s.user.id = :userId " +
            "ORDER BY p.price DESC ")
    List<Plan> findAllByUserIdOrderedByPriceDesc(String userId);

    boolean existsByName(String name);
}
