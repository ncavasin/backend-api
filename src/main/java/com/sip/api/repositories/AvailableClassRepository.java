package com.sip.api.repositories;

import com.sip.api.domains.availableClass.AvailableClass;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface AvailableClassRepository extends JpaRepository<AvailableClass, String> {

    @Override
    @NonNull
    List<AvailableClass> findAll();

    void deleteById(@NonNull String id);

    boolean existsById(@NonNull String id);

    Optional<AvailableClass> findByActivity_IdAndTimeslotId(@NotNull String activityId, @NotNull String timeslotId);
}
