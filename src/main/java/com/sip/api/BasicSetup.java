package com.sip.api;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.domains.user.User;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.dtos.availableClass.AvailableClassesCreationDto;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.timeslot.TimeslotCreationDto;
import com.sip.api.dtos.user.UserCreationDto;
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

    private User professor;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        try {
            createResources();
        } catch (Exception e) {
            log.error("Error creating resources. Message: {}", e.getMessage());
        }
        try {
            createRoles();
        } catch (Exception e) {
            log.error("Error creating roles. Message: {}", e.getMessage());
        }
        try {
            createTimeslots();
        } catch (Exception e) {
            log.error("Error creating timeslots. Message: {}", e.getMessage());
        }
        try {
            if (!userService.existsByEmail(superAdminEmail)) {
                User user = userService.createUser(UserCreationDto.builder()
                        .dni(99999999)
                        .email(superAdminEmail)
                        .password(superAdminPassword)
                        .phone(22233325L)
                        .rolesNames(Collections.singletonList("ROLE_ADMIN"))
                        .build());
                userService.activateUser(user.getId());
                log.info("Admin created");
            }
        } catch (Exception e) {
            log.error("Error creating admin. Message: {}", e.getMessage());
        }
        try {

            String professorEmail = "professor@mail.com";
            if (!userService.existsByEmail(professorEmail)) {
                professor = userService.createUser(UserCreationDto.builder()
                        .dni(123987)
                        .email(professorEmail)
                        .password("12345678")
                        .rolesNames(Collections.singletonList("ROLE_PROFESSOR"))
                        .build());
                userService.activateUser(professor.getId());
                log.info("Professor created");
            }
        } catch (Exception e) {
            log.error("Error creating professor. Message: {}", e.getMessage());
        }
        try {
            createActivities();
        } catch (Exception e) {
            log.error("Error creating activities. Message: {}", e.getMessage());
        }
        try {
            createAvailableClasses();
        } catch (Exception e) {
            log.error("Error creating available classes. Message: {}", e.getMessage());
        }
    }

    private void createActivities() {
        try {
            activityService.createActivity(ActivityCreationDto.builder()
                    .name("CROSSFIT")
                    .basePrice(2250.75D)
                    .professor(UserConverter.entityToDtoSlim(professor))
                    .attendeesLimit(12)
                    .build());
            activityService.createActivity(ActivityCreationDto.builder()
                    .name("SPINNING")
                    .basePrice(3550.25D)
                    .professor(UserConverter.entityToDtoSlim(professor))
                    .attendeesLimit(20)
                    .build());
            activityService.createActivity(ActivityCreationDto.builder()
                    .name("BOXING")
                    .basePrice(1050.10D)
                    .professor(UserConverter.entityToDtoSlim(professor))
                    .attendeesLimit(6)
                    .build());
        } catch (Exception e) {
            log.warn("Activities already created, skipping...");
        }
    }


    private void createAvailableClasses() {
        try {
            createAvailableClass(1, 0);
            createAvailableClass(2, 1);
            createAvailableClass(1, 2);
        } catch (Exception e) {
            log.warn("Available classes already created, skipping...");
        }
    }

    private void createAvailableClass(int activityPosition, int timeslotPosition) {
        Activity activity = activityService.findAll().get(activityPosition);
        Timeslot timeslot = timeslotService.findAll().get(timeslotPosition);
        availableClassService.createAvailableClass(AvailableClassesCreationDto.builder()
                .activityId(activity.getId())
                .timeslotId(timeslot.getId())
                .build());
    }

    public DayOfWeek getRandomDay() {
        return DayOfWeek.values()[(int) (Math.random() * 5)];
    }

    private void createTimeslots() {
        try {
            for (int i = 7; i < 13; i++) {
                timeslotService.createTimeslot(TimeslotCreationDto.builder()
                        .startTime(LocalTime.of(i, 15))
                        .endTime(LocalTime.of(i + 1, 15))
                        .dayOfWeek(getRandomDay())
                        .build());
            }

            for (int i = 16; i < 22; i++) {
                timeslotService.createTimeslot(TimeslotCreationDto.builder()
                        .startTime(LocalTime.of(i, 15))
                        .endTime(LocalTime.of(i + 1, 15))
                        .dayOfWeek(getRandomDay())
                        .build());
            }
        } catch (BadRequestException e) {
            // do nothing as timeslots are already created
        }
    }

    private void createResources() {
        if (!resourceService.existsByUrl("/*"))
            resourceService.createResource(ResourceCreationDto.builder()
                    .name("ALL")
                    .url("/*")
                    .build());
        if (!resourceService.existsByUrl("/user"))
            resourceService.createResource(ResourceCreationDto.builder()
                    .name("USER")
                    .url("/user")
                    .build());
        if (!resourceService.existsByUrl("/management"))
            resourceService.createResource(ResourceCreationDto.builder()
                    .name("MANAGEMENT")
                    .url("/management")
                    .build());
        if (!resourceService.existsByUrl("/activity"))
            resourceService.createResource(ResourceCreationDto.builder()
                    .name("ACTIVITY")
                    .url("/activity")
                    .build());
        if (!resourceService.existsByUrl("/login"))
            resourceService.createResource(ResourceCreationDto.builder()
                    .name("LOGIN")
                    .url("/login")
                    .build());
        if (!resourceService.existsByUrl("/logout"))
            resourceService.createResource(ResourceCreationDto.builder()
                    .name("LOGOUT")
                    .url("/logout")
                    .build());
    }

    private void createRoles() {
        if (!roleService.existsByName("ROLE_ADMIN"))
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_ADMIN")
                    .allowedResourcesIds(List.of(resourceService.findByName("ALL").getId()))
                    .build());
        if (!roleService.existsByName("ROLE_PROFESSOR"))
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_PROFESSOR")
                    .allowedResourcesIds(List.of(resourceService.findByName("LOGIN").getId(),
                            resourceService.findByName("LOGOUT").getId(),
                            resourceService.findByName("ACTIVITY").getId(),
                            resourceService.findByName("USER").getId()))
                    .build());
        if (!roleService.existsByName("ROLE_USER"))
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_USER")
                    .allowedResourcesIds(List.of(resourceService.findByName("LOGIN").getId(),
                            resourceService.findByName("LOGOUT").getId(),
                            resourceService.findByName("USER").getId()))
                    .build());
    }
}
