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


    @Query(nativeQuery = true,
            value = "SELECT p.* FROM plan p " +
                    "JOIN plan_subscriptions ps on p.id = ps.plan_id " +
                    "JOIN subscription s on ps.subscription_id = s.id " +
                    "WHERE s.user_id = :userId AND p.price = " +
                    "(SELECT MAX(p.price) " +
                    "FROM plan p2 " +
                    "JOIN plan_subscriptions ps2 ON p2.id = ps2.plan_id " +
                    "JOIN subscription s2 ON s2.id = ps2.subscription_id " +
                    "WHERE s.user_id = :userId)")
    Optional<Plan> findMostExpensivePlanByUser(String userId);

    boolean existsByName(String name);
}
