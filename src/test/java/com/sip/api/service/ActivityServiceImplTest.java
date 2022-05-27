package com.sip.api.service;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.user.User;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.dtos.activity.ActivityDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.services.ActivityService;
import com.sip.api.services.RoleService;
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

@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class ActivityServiceImplTest {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private Activity savedActivity;
    private User professor;
    private final String name = "NEW ACTIVITY";
    private final Double basePrice = 2750.25D;
    private final int attendeesLimit = 6;

    @Before
    @Transactional
    public void setUp() {
        professor = userService.findByEmail(UserEmailDto.builder().email("professor@mail.com").build());
        savedActivity = generateActivity(basePrice, professor, attendeesLimit);
    }

    @Test
    @Transactional
    public void createActivity() {
        Assert.assertEquals(savedActivity, activityService.findById(savedActivity.getId()));
    }

    @Test
    @Transactional
    public void createActivityWithSameName_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class, () -> generateActivity(basePrice, professor, attendeesLimit));
    }

    @Test
    @Transactional
    public void createActivityWithNegativeAttendeesLimit_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class, () -> generateActivity(basePrice, professor, -1));
    }

    @Test
    @Transactional
    public void updateActivityName() {

    }

    @Test
    @Transactional
    public void updateActivityBasePrice() {
        final Double newPrice = 5732D;
        final Activity found = activityService.findById(savedActivity.getId());
        Assert.assertNotEquals(found.getBasePrice(), newPrice);

        final Activity updated = activityService.updateActivity(found.getId(), ActivityDto.builder()
                .id(found.getId())
                .basePrice(newPrice)
                .attendeesLimit(found.getAttendeesLimit())
                .professor(UserConverter.entityToDtoSlim(userService.findById(professor.getId())))
                .build());
        Assert.assertEquals(updated.getBasePrice(), newPrice);
    }

    @Test
    @Transactional
    public void updateActivityNegativeBasePrice_shouldThrowBadRequest() {
        final Double newPrice = -1527D;
        final Activity found = activityService.findById(savedActivity.getId());
        Assert.assertNotEquals(found.getBasePrice(), newPrice);

        Assert.assertThrows(BadRequestException.class, () -> activityService.updateActivity(found.getId(), ActivityDto.builder()
                .id(found.getId())
                .basePrice(newPrice)
                .attendeesLimit(found.getAttendeesLimit())
                .professor(UserConverter.entityToDtoSlim(userService.findById(professor.getId())))
                .build()));
    }

    @Test
    @Transactional
    public void updateActivityAttendeesLimit() {
        final int newAttendeeLimit = 7;
        final Activity found = activityService.findById(savedActivity.getId());
        Assert.assertNotEquals(found.getAttendeesLimit(), newAttendeeLimit);

        final Activity updated = activityService.updateActivity(found.getId(), ActivityDto.builder()
                .id(found.getId())
                .basePrice(found.getBasePrice())
                .attendeesLimit(newAttendeeLimit)
                .professor(UserConverter.entityToDtoSlim(userService.findById(professor.getId())))
                .build());
        Assert.assertEquals(updated.getAttendeesLimit(), newAttendeeLimit);
    }

    @Test
    @Transactional
    public void updateActivityNegativeAttendeesLimit_shouldThrowBadRequest() {
        final int newAttendeeLimit = -10;
        final Activity found = activityService.findById(savedActivity.getId());
        Assert.assertNotEquals(found.getAttendeesLimit(), newAttendeeLimit);

        Assert.assertThrows(BadRequestException.class, () -> activityService.updateActivity(found.getId(), ActivityDto.builder()
                .id(found.getId())
                .basePrice(found.getBasePrice())
                .attendeesLimit(newAttendeeLimit)
                .professor(UserConverter.entityToDtoSlim(userService.findById(professor.getId())))
                .build()));
    }

    @Test
    @Transactional
    public void updateActivityProfessorRole() {
        final User newProfessor = userService.findByEmail(UserEmailDto.builder().email("admin@admin.com").build());
        final Activity found = activityService.findById(savedActivity.getId());
        Assert.assertNotEquals(found.getProfessor().getId(), newProfessor.getId());


        final Activity updated = activityService.updateActivity(found.getId(), ActivityDto.builder()
                .id(found.getId())
                .basePrice(found.getBasePrice())
                .attendeesLimit(found.getAttendeesLimit())
                .professor(UserConverter.entityToDtoSlim(newProfessor))
                .build());
        Assert.assertEquals(updated.getProfessor().getId(), newProfessor.getId());
    }

    @Test
    @Transactional
    public void updateActivityProfessorWithoutRole_shouldThrowBadRequest() {
        final User newProfessor = userService.findByEmail(UserEmailDto.builder().email("analyst@mail.com").build());
        final Activity found = activityService.findById(savedActivity.getId());
        Assert.assertNotEquals(found.getProfessor().getId(), newProfessor.getId());

        Assert.assertThrows(BadRequestException.class, () -> activityService.updateActivity(found.getId(), ActivityDto.builder()
                .id(found.getId())
                .basePrice(found.getBasePrice())
                .attendeesLimit(found.getAttendeesLimit())
                .professor(UserConverter.entityToDtoSlim(newProfessor))
                .build()));
    }

    @Test
    @Transactional
    public void deleteActivity() {
        final Activity found = activityService.findById(savedActivity.getId());
        activityService.delete(found.getId());
        Assert.assertThrows(NotFoundException.class, () -> activityService.findById(found.getId()));
    }

    private Activity generateActivity(Double basePrice, User professor, int attendeesLimit) {
        return activityService.createActivity(
                ActivityCreationDto.builder()
                        .name("NEW ACTIVITY")
                        .basePrice(basePrice)
                        .professor(UserConverter.entityToDtoSlim(userService.findById(professor.getId())))
                        .attendeesLimit(attendeesLimit)
                        .build());
    }
}
