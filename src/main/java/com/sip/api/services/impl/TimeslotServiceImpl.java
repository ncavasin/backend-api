package com.sip.api.services.impl;

import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.domains.timeslot.TimeslotConverter;
import com.sip.api.dtos.timeslot.TimeslotDto;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.TimeslotRepository;
import com.sip.api.services.TimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {
    private final TimeslotRepository timeslotRepository;

    @Override
    public List<Timeslot> findAll() {
        return timeslotRepository.findAll();
    }

    @Override
    public Timeslot findById(String timeslotId) {
        return timeslotRepository.findById(timeslotId).orElseThrow(() -> new NotFoundException("Timeslot not found"));
    }

    @Override
    public Timeslot createTimeslot(TimeslotDto timeslotDto) {
        return timeslotRepository.save(TimeslotConverter.fromDtoToEntity(timeslotDto));
    }

    @Override
    public Timeslot updateTimeslot(String timeslotId, TimeslotDto timeslotDto) {
        if(!timeslotRepository.existsById(timeslotId)) throw new NotFoundException("Timeslot not found");
        Timeslot timeslot = findById(timeslotId);
        timeslot.setStartTime(timeslotDto.getStartTime());
        timeslot.setEndTime(timeslotDto.getEndTime());
        timeslot.setAppointments(timeslotDto.getAppointments());
        return timeslotRepository.save(timeslot);
    }

    @Override
    public void deleteTimeslot(String timeslotId) {
        timeslotRepository.deleteById(timeslotId);
    }
}