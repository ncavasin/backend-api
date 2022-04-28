package com.sip.api.services;

import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.dtos.timeslot.TimeslotDto;

import java.util.List;

public interface TimeslotService {
    List<Timeslot> findAll();

    Timeslot findById(String timeslotId);

    Timeslot createTimeslot(TimeslotDto timeslotDto);

    Timeslot updateTimeslot(String timeslotId, TimeslotDto timeslotDto);

    void deleteTimeslot(String timeslotId);
}
