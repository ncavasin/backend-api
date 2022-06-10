package com.sip.api.repositories;

import com.sip.api.domains.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    @Query("SELECT p FROM Payment p WHERE p.subscription.plan.id = :planId")
    List<Payment> findAllByPlanId(String planId);

    @Query("SELECT p FROM Payment p WHERE p.subscription.user.id = :userId")
    List<Payment> findAllByUserId(String userId);

    Optional<Payment> findBySubscriptionId(String subscriptionId);


}
