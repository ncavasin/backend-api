package com.sip.api.repositories;

import com.sip.api.domains.activity.Activity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    @Override
    @NonNull
    List<Activity> findAll();

    boolean existsByName(String name);
}
