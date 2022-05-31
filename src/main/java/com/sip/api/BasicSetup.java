package com.sip.api;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.domains.user.User;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.dtos.availableClass.AvailableClassCreationDto;
import com.sip.api.dtos.timeslot.TimeslotCreationDto;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class BasicSetup implements ApplicationRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final ResourceService resourceService;
    private final ActivityService activityService;
    private final TimeslotService timeslotService;
    private final AvailableClassService availableClassService;

    @Value("${superadmin-email}")
    private String superAdminEmail;
    @Value("${superadmin-password}")
    private String superAdminPassword;
    String professorEmail = "professor@mail.com";

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        createTimeslots();
        createActivities();
        createAvailableClasses();
    }
    private void createActivities() {
        createActivity("CROSSFIT", 2250.75D, 12);
        createActivity("SPINNING", 3550.25D, 20);
        createActivity("BOXING", 1050.10D, 6);
        createActivity("TESTING_ACTIVITY", 1050.10D, 1);

    }

    private void createActivity(String activityName, double basePrice, int attendeesLimit) {
        try {
            User professor = userService.findByEmail(UserEmailDto.builder().email(professorEmail).build());
            activityService.createActivity(ActivityCreationDto.builder()
                    .name(activityName)
                    .basePrice(basePrice)
                    .professor(UserConverter.entityToDtoSlim(professor))
                    .attendeesLimit(attendeesLimit)
                    .build());
        } catch (BadRequestException e) {
            log.warn("Activity already created, skipping...");
        }
    }

    private void createAvailableClasses() {
        createAvailableClass(1, 0);
        createAvailableClass(2, 1);
        createAvailableClass(1, 2);
        createAvailableClass(3, 3);
    }

    private void createAvailableClass(int activityPosition, int timeslotPosition) {
        try {
            Activity activity = activityService.findAll().get(activityPosition);
            Timeslot timeslot = timeslotService.findAll().get(timeslotPosition);
            availableClassService.createAvailableClass(AvailableClassCreationDto.builder()
                    .activityId(activity.getId())
                    .timeslotId(timeslot.getId())
                    .build());
        } catch (BadRequestException e) {
            log.warn("Available class already created, skipping...");
        }
    }

    public DayOfWeek getRandomDay() {
        return DayOfWeek.values()[(int) (Math.random() * 5)];
    }

    private void createTimeslots() {
        try {
            for (int i = 7; i < 13; i++) {
                LocalTime startTime = LocalTime.of(i, 15);
                LocalTime endTime = startTime.plusHours(1);
                DayOfWeek dayOfWeek = getRandomDay();
                timeslotService.createTimeslot(TimeslotCreationDto.builder()
                        .startTime(startTime)
                        .endTime(endTime)
                        .dayOfWeek(dayOfWeek)
                        .build());
                log.info("Timeslot on {} from {} to {} created.", dayOfWeek, startTime, endTime);
            }

            for (int i = 16; i < 22; i++) {
                LocalTime startTime = LocalTime.of(i, 15);
                LocalTime endTime = startTime.plusHours(1);
                DayOfWeek dayOfWeek = getRandomDay();
                timeslotService.createTimeslot(TimeslotCreationDto.builder()
                        .startTime(startTime)
                        .endTime(endTime)
                        .dayOfWeek(dayOfWeek)
                        .build());
                log.info("Timeslot on {} from {} to {} created.", dayOfWeek, startTime, endTime);
            }
        } catch (BadRequestException e) {
            log.info("Timeslots already created, skipping...");
        }
    }
}
