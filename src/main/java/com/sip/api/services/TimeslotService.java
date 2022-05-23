package com.sip.api.services;

import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.dtos.timeslot.TimeslotCreationDto;
import com.sip.api.dtos.timeslot.TimeslotDto;

import java.util.List;

public interface TimeslotService {
    List<Timeslot> findAll();

    List<Timeslot> findAllAvailableAndOrdered();

    Timeslot findById(String timeslotId);

    Timeslot createTimeslot(TimeslotCreationDto timeslotCreationDto);

    Timeslot updateTimeslot(String timeslotId, TimeslotDto timeslotDto);

    void deleteTimeslot(String timeslotId);
}
