package com.sip.api.services;

import com.sip.api.domains.activity.Activity;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.dtos.activity.ActivityDto;

import java.util.List;

public interface ActivityService {

    List<Activity> findAll();

    Activity findById(String activityId);

    Activity createActivity(ActivityCreationDto activityCreationDto);

    Activity updateActivity(String activityId, ActivityDto activityDto);

    void delete(String activityId);
}
