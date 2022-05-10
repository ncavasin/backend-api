package com.sip.api.services.impl;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.dtos.availableClass.AvailableClassesCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.repositories.AvailableClassRepository;
import com.sip.api.services.ActivityService;
import com.sip.api.services.AvailableClassService;
import com.sip.api.services.TimeslotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvailableClassServiceImpl implements AvailableClassService {
    private final AvailableClassRepository availableClassRepository;
    private final ActivityService activityService;
    private final TimeslotService timeslotService;

    @Override
    public List<AvailableClass> findAll() {
        return availableClassRepository.findAll();
    }

    @Override
    public AvailableClass findById(String availableClassId) {
        return availableClassRepository.findById(availableClassId).orElseThrow(() -> new BadRequestException("AvailableClass not found!"));
    }

    @Override
    public AvailableClass createAvailableClass(AvailableClassesCreationDto availableClassesCreationDto) {
        if (availableClassRepository.findByActivity_IdAndTimeslotId(availableClassesCreationDto.getActivityId(), availableClassesCreationDto.getTimeslotId()).isPresent())
            throw new BadRequestException("AvailableClass already exists!");
        Activity activity = activityService.findById(availableClassesCreationDto.getActivityId());
        Timeslot timeslot = timeslotService.findById(availableClassesCreationDto.getTimeslotId());

        return availableClassRepository.save(AvailableClass.builder()
                .activity(activity)
                .timeslot(timeslot)
                .build());
    }

    @Override
    public void removeAvailableClass(String availableClassId) {
        if (!availableClassRepository.existsById(availableClassId))
            throw new BadRequestException("AvailableClass not found!");
        availableClassRepository.deleteById(availableClassId);
    }
}
