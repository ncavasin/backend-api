package com.sip.api.repositories;

import com.sip.api.domains.subscription.Subscription;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    @NonNull List<Subscription> findAll();

    @Query("delete from Subscription s where s.user.id = ?1")
    @Transactional
    @Modifying
    void deleteAllByUserId(String userId);

    @Query("select s from Subscription s where s.user.id = ?1")
    List<Subscription> findAllByUserId(String userId);

    @Query("select s from Subscription s where s.plan.id = ?1")
    List<Subscription> findAllByPlanId(String planId);

    @Query("select s from Subscription s where s.user.id = ?1 and s.plan.id = ?2")
    List<Subscription> findAllByUserIdAndPlanId(String userId, String planId);

    @Query("select (count(s) > 0) from Subscription s where s.user.id = ?1")
    boolean existsByUserId(String userId);
}
