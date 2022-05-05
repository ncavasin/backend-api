package com.sip.api.controllers;

import com.sip.api.domains.activity.ActivityConverter;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.dtos.activity.ActivityDto;
import com.sip.api.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping("/all")
    public List<ActivityDto> findAll() {
        return ActivityConverter.fromEntityToDto(activityService.findAll());
    }

    @GetMapping("/{activityId}")
    public ActivityDto findById(@PathVariable("activityId") String activityId) {
        return ActivityConverter.fromEntityToDto(activityService.findById(activityId));
    }

    @PostMapping
    public ActivityDto createActivity(ActivityCreationDto activityCreationDto) {
        return ActivityConverter.fromEntityToDto(activityService.createActivity(activityCreationDto));
    }

    @PutMapping("/{activityId}")
    public ActivityDto updateActivity(@PathVariable("activityId") String activityId, @RequestBody ActivityDto activityDto) {
        return ActivityConverter.fromEntityToDto(activityService.updateActivity(activityId, activityDto));
    }

    @DeleteMapping("/{activityId}")
    public void deleteActivity(@PathVariable("activityId") String activityId) {
        activityService.delete(activityId);
    }
}
