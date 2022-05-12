package com.sip.api.repositories;

import com.sip.api.domains.availableClass.AvailableClass;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableClassRepository extends JpaRepository<AvailableClass, String> {

    @Override
    @NonNull
    List<AvailableClass> findAll();

    void deleteById(@NonNull String id);

    boolean existsById(@NonNull String id);

    boolean existsByActivityIdAndTimeslotId(@NonNull String activityId, @NonNull String timeslotId);
}
