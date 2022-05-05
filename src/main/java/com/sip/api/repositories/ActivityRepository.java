package com.sip.api.repositories;

import com.sip.api.domains.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    @Override
    List<Activity> findAll();
}
