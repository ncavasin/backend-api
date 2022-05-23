package com.sip.api.repositories;

import com.sip.api.domains.timeslot.Timeslot;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;

public interface TimeslotRepository extends JpaRepository<Timeslot, String> {

    @NonNull List<Timeslot> findAll();

    @Query("SELECT t " +
            "FROM Timeslot t " +
            "WHERE t.id NOT IN " +
                "(SELECT ac.timeslot FROM AvailableClass ac)" +
            "ORDER BY t.dayOfWeek, t.startTime, t.endTime ASC")
    List<Timeslot> findAllAvailableAndOrderedByStartTimeAAndDayOfWeek();

    boolean existsByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);
}
