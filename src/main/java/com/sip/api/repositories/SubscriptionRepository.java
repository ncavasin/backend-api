package com.sip.api.repositories;

import com.sip.api.domains.subscription.Subscription;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    @NonNull List<Subscription> findAll();

    @Query("select s from Subscription s where s.user.id = ?1")
    List<Subscription> findAllByUserId(String userId);

    @Query("select (count(s) > 0) from Subscription s where s.user.id = ?1")
    boolean existsByUserId(String userId);
}
