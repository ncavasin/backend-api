package com.sip.api.services.impl;


import com.sip.api.domains.activity.Activity;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.dtos.activity.ActivityDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.repositories.ActivityRepository;
import com.sip.api.services.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public Activity findById(String activityId) {
        return null;
    }

    @Override
    public Activity createActivity(ActivityCreationDto activityCreationDto) {
        return null;
    }

    @Override
    public Activity updateActivity(String activityId, ActivityDto activityDto) {
        return null;
    }

    @Override
    public void delete(String activityId) {
        if(! activityRepository.existsById(activityId))
            throw new BadRequestException("Activity not found!");
        activityRepository.deleteById(activityId);
    }
}
