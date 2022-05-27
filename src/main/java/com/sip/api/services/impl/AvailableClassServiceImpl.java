package com.sip.api.services.impl;

import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.dtos.availableClass.AvailableClassDto;
import com.sip.api.dtos.availableClass.AvailableClassesCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.AvailableClassRepository;
import com.sip.api.services.ActivityService;
import com.sip.api.services.AvailableClassService;
import com.sip.api.services.TimeslotService;
import com.sip.api.services.UserService;
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
    private final UserService userService;

    @Override
    public List<AvailableClass> findAll() {
        return availableClassRepository.findAll();
    }

    @Override
    public AvailableClass findById(String availableClassId) {
        return availableClassRepository.findById(availableClassId).orElseThrow(() -> new NotFoundException("AvailableClass not found!"));
    }

    @Override
    public List<AvailableClass> findByActivityId(String activityId) {
        return availableClassRepository.findAllByActivityId(activityId);
    }

    @Override
    public AvailableClass createAvailableClass(AvailableClassesCreationDto availableClassesCreationDto) {
        checkAvailableClassDoesNotExist(availableClassesCreationDto.getActivityId(), availableClassesCreationDto.getTimeslotId());
        return availableClassRepository.save(AvailableClass.builder()
                .activity(activityService.findById(availableClassesCreationDto.getActivityId()))
                .timeslot(timeslotService.findById(availableClassesCreationDto.getTimeslotId()))
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
        log.info("AvailableClass {} deleted.", availableClassId);
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
