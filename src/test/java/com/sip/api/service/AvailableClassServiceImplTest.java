package com.sip.api.service;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.activity.ActivityConverter;
import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.domains.timeslot.TimeslotConverter;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.dtos.activity.ActivityDto;
import com.sip.api.dtos.availableClass.AvailableClassCreationDto;
import com.sip.api.dtos.availableClass.AvailableClassDto;
import com.sip.api.dtos.timeslot.TimeslotCreationDto;
import com.sip.api.dtos.timeslot.TimeslotDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.services.ActivityService;
import com.sip.api.services.AvailableClassService;
import com.sip.api.services.TimeslotService;
import com.sip.api.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class AvailableClassServiceImplTest {
    @Autowired
    private AvailableClassService availableClassService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TimeslotService timeslotService;
    @Autowired
    private UserService userService;

    private AvailableClass savedAvailableClass;
    private Activity boxingActivity;
    private Timeslot boxingTimeslot;

    @Before
    @Transactional
    public void setUp() {
        boxingTimeslot = generateTimeslot(LocalTime.of(14, 0, 0), LocalTime.of(15, 0, 0), DayOfWeek.SUNDAY);
        boxingActivity = generateActivity("NEW BOXING ACTIVITY");
        savedAvailableClass = generateAvailableClass(boxingActivity.getId(), boxingTimeslot.getId());
    }

    @Test
    @Transactional
    public void createAvailableClass() {
        Assert.assertEquals(savedAvailableClass, availableClassService.findById(savedAvailableClass.getId()));
    }

    @Test
    @Transactional
    public void createAvailableClassWithNonExistentActivity_shouldThrowNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> generateAvailableClass("NON_EXISTENT_ID", boxingTimeslot.getId()));
    }

    @Test
    @Transactional
    public void createAvailableClassWithNonExistentTimeslot_shouldThrowNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> generateAvailableClass(boxingActivity.getId(), "NON_EXISTENT_ID"));
    }

    @Test
    @Transactional
    public void findByActivityId() {
        final List<AvailableClass> availableClassList = availableClassService.findByActivityId(savedAvailableClass.getActivity().getId());
        Assert.assertEquals(savedAvailableClass, availableClassList.get(0));
    }

    @Test
    @Transactional
    public void updateAvailableClassActivity() {
        final AvailableClass found = availableClassService.findById(savedAvailableClass.getId());
        Assert.assertEquals(found.getActivity().getId(), savedAvailableClass.getActivity().getId());

        final Activity newActivity = generateActivity("ACTIVITY FOR UPDATE");
        final AvailableClass updated = availableClassService.updateAvailableClass(AvailableClassDto.builder()
                .id(savedAvailableClass.getId())
                .activityDto(ActivityConverter.fromEntityToDto(newActivity))
                .timeslotDto(TimeslotConverter.fromEntityToDto(boxingTimeslot))
                .build());
        Assert.assertEquals(updated.getActivity().getId(), newActivity.getId());
    }

    @Test
    @Transactional
    public void updateAvailableClassWithNonExistentActivity_shouldThrowBadRequest() {
        final AvailableClass found = availableClassService.findById(savedAvailableClass.getId());
        Assert.assertEquals(found.getActivity().getId(), savedAvailableClass.getActivity().getId());

        Assert.assertThrows(NotFoundException.class, () -> availableClassService.updateAvailableClass(AvailableClassDto.builder()
                .id(savedAvailableClass.getId())
                .activityDto(generateEmptyActivityDto())
                .timeslotDto(TimeslotConverter.fromEntityToDto(boxingTimeslot))
                .build()));
    }

    @Test
    @Transactional
    public void updateAvailableClassWithNonExistentTimeslot_shouldThrowNotFound() {
        final AvailableClass found = availableClassService.findById(savedAvailableClass.getId());
        Assert.assertEquals(found.getActivity().getId(), savedAvailableClass.getActivity().getId());

        Assert.assertThrows(NotFoundException.class, () -> availableClassService.updateAvailableClass(AvailableClassDto.builder()
                .id(savedAvailableClass.getId())
                .activityDto(ActivityConverter.fromEntityToDto(boxingActivity))
                .timeslotDto(generateEmptyTimeslotDto())
                .build()));
    }

    @Test
    @Transactional
    public void updateAvailableClassTimeslot() {
        final AvailableClass found = availableClassService.findById(savedAvailableClass.getId());
        Assert.assertEquals(found.getTimeslot().getId(), savedAvailableClass.getTimeslot().getId());

        final Timeslot newTimeslot = generateTimeslot(LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0), DayOfWeek.SUNDAY);
        final AvailableClass updated = availableClassService.updateAvailableClass(AvailableClassDto.builder()
                .id(savedAvailableClass.getId())
                .activityDto(ActivityConverter.fromEntityToDto(boxingActivity))
                .timeslotDto(TimeslotConverter.fromEntityToDto(newTimeslot))
                .build());
        Assert.assertEquals(updated.getTimeslot().getId(), newTimeslot.getId());
    }

    @Test
    @Transactional
    public void deleteAvailableClass() {
        Assert.assertEquals(savedAvailableClass, availableClassService.findById(savedAvailableClass.getId()));
        availableClassService.removeAvailableClass(savedAvailableClass.getId());
        Assert.assertThrows(NotFoundException.class, () -> availableClassService.findById(savedAvailableClass.getId()));
    }

    private AvailableClass generateAvailableClass(String activityId, String timeslotId) {
        return availableClassService.createAvailableClass(AvailableClassCreationDto.builder()
                .activityId(activityId)
                .timeslotId(timeslotId)
                .build());
    }

    private Activity generateActivity(String activityName) {
        return activityService.createActivity(ActivityCreationDto.builder()
                .name(activityName)
                .professor(UserConverter.entityToDtoSlim(userService.findByEmail(UserEmailDto.builder().email("professor@mail.com").build())))
                .basePrice(50.25)
                .attendeesLimit(2)
                .build());
    }

    private Timeslot generateTimeslot(LocalTime startTime, LocalTime endTime, DayOfWeek day) {
        return timeslotService.createTimeslot(TimeslotCreationDto.builder()
                .startTime(startTime)
                .endTime(endTime)
                .dayOfWeek(day)
                .build());
    }

    private TimeslotDto generateEmptyTimeslotDto() {
        return TimeslotDto.builder()
                .id("NON_EXISTENT_ID")
                .dayOfWeek(DayOfWeek.SATURDAY)
                .startTime(LocalTime.of(4, 0, 0))
                .endTime(LocalTime.of(5, 0, 0))
                .build();
    }

    private ActivityDto generateEmptyActivityDto() {
        return ActivityDto.builder()
                .id("NON_EXISTENT_ID")
                .name("NON_EXISTENT_NAME")
                .basePrice(1.25D)
                .professor(UserConverter.entityToDtoSlim(userService.findByEmail(UserEmailDto.builder().email("professor@mail.com").build())))
                .attendeesLimit(1)
                .build();
    }
}
