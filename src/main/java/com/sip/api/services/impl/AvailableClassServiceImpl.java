package com.sip.api.services.impl;

import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.dtos.availableClass.AvailableClassCreationDto;
import com.sip.api.dtos.availableClass.AvailableClassDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.repositories.AvailableClassRepository;
import com.sip.api.services.ActivityService;
import com.sip.api.services.AvailableClassService;
import com.sip.api.services.TimeslotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<AvailableClass> findByActivityId(String activityId) {
        return availableClassRepository.findAllByActivityId(activityId);
    }

    @Override
    public AvailableClass createAvailableClass(AvailableClassCreationDto availableClassCreationDto) {
        checkAvailableClassDoesNotExist(availableClassCreationDto.getActivityId(), availableClassCreationDto.getTimeslotId());
        return availableClassRepository.save(AvailableClass.builder()
                .activity(activityService.findById(availableClassCreationDto.getActivityId()))
                .timeslot(timeslotService.findById(availableClassCreationDto.getTimeslotId()))
                .build());
    }

    private void checkAvailableClassDoesNotExist(String activityId, String timeslotId) {
        if (availableClassRepository.existsByActivityIdAndTimeslotId(activityId, timeslotId))
            throw new BadRequestException("AvailableClass already exists!");
    }

    @Override
    public void removeAvailableClass(String availableClassId) {
        if (!availableClassRepository.existsById(availableClassId))
            throw new BadRequestException("AvailableClass not found!");
        availableClassRepository.deleteById(availableClassId);
    }

    @Override
    public AvailableClass updateAvailableClass(AvailableClassDto availableClassDto) {
        AvailableClass availableClass = findById(availableClassDto.getId());
        availableClass.setActivity(activityService.findById(availableClassDto.getActivityDto().getId()));
        availableClass.setTimeslot(timeslotService.findById(availableClassDto.getTimeslotDto().getId()));
        return availableClassRepository.save(availableClass);
    }

    private void checkAvailableClassDoesExist(String availableClassId) {
        if (!availableClassRepository.existsById(availableClassId))
            throw new BadRequestException(String.format("AvailableClass '%s' does not exist!", availableClassId));
    }
}
