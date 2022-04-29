package com.sip.api.repositories;

import com.sip.api.domains.timeslot.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeslotRepository extends JpaRepository<Timeslot, String> {

    List<Timeslot> findAll();
}
