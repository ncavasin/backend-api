package com.sip.api.services.impl;

import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.availableClass.AvailableClassesCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.repositories.AvailableClassRepository;
import com.sip.api.services.ActivityService;
import com.sip.api.services.AvailableClassService;
import com.sip.api.services.TimeslotService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
        return availableClassRepository.findById(availableClassId).orElseThrow(() -> new BadRequestException("AvailableClass not found!"));
    }

    @Override
    public AvailableClass createAvailableClass(AvailableClassesCreationDto availableClassesCreationDto) {
        checkAvailableClassDoesNotExist(availableClassesCreationDto.getActivityId(), availableClassesCreationDto.getTimeslotId());
        log.info("AvailableClass created.");
        return availableClassRepository.save(AvailableClass.builder()
                .activity(activityService.findById(availableClassesCreationDto.getActivityId()))
                .timeslot(timeslotService.findById(availableClassesCreationDto.getTimeslotId()))
                .attendees(new HashSet<>())
                .build());
    }

    private void checkAvailableClassDoesNotExist(String activityId, String timeslotId) {
        if (availableClassRepository.existsByActivityIdAndTimeslotId(activityId, timeslotId))
            throw new BadRequestException("AvailableClass already exists!");
    }

    @Override
    public AvailableClass addUserToAvailableClass(String availableClassId, String userId) {
        checkAvailableClassDoesExist(availableClassId);
        AvailableClass availableClass = findById(availableClassId);
        User attendee = userService.findById(userId);
        availableClass.addAttendee(attendee);
        log.info("Attendee '{}' booked a slot in AvailableClass {}.", attendee.getId(), availableClass.getId());
        return availableClassRepository.save(availableClass);
    }

    @Override
    public AvailableClass removeUserFromAvailableClass(String availableClassId, String userId) {
        checkAvailableClassDoesExist(availableClassId);
        AvailableClass availableClass = findById(availableClassId);
        User attendee = userService.findById(userId);
        availableClass.removeAttendee(attendee);
        log.info("Attendee '{}' left his slot in AvailableClass {}.", attendee.getId(), availableClass.getId());
        return availableClassRepository.save(availableClass);
    }

    @Override
    public void removeAvailableClass(String availableClassId) {
        if (!availableClassRepository.existsById(availableClassId))
            throw new BadRequestException("AvailableClass not found!");
        log.info("AvailableClass {} deleted.", availableClassId);
        availableClassRepository.deleteById(availableClassId);
    }

    private void checkAvailableClassDoesExist(String availableClassId) {
        if (!availableClassRepository.existsById(availableClassId))
            throw new BadRequestException(String.format("AvailableClass '%s' does not exist!", availableClassId));
    }
}
