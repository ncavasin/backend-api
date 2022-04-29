package com.sip.api.repositories;

import com.sip.api.domains.timeslot.Timeslot;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface TimeslotRepository extends JpaRepository<Timeslot, String> {

    @NonNull List<Timeslot> findAll();

    boolean existsByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);
}
