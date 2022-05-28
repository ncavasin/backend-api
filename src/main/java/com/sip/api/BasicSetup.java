package com.sip.api;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.domains.user.User;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.dtos.availableClass.AvailableClassCreationDto;
import com.sip.api.dtos.resource.ResourceCreationDto;
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
        createResources();
        createRoles();
        createTimeslots();
        createAdminUser();
        createProfessorUser();
        createAnalystUser();
        createActivities();
        createAvailableClasses();
    }

    private void createAnalystUser() {
        try {
            String analystMail = "analyst@mail.com";
            if (!userService.existsByEmail(analystMail)) {
                User analyst = userService.createUser(UserCreationDto.builder()
                        .dni(00000)
                        .email(analystMail)
                        .password("12345678")
                        .rolesNames(Collections.singletonList("ROLE_ANALYST"))
                        .build());
                userService.activateUser(analyst.getId());
                log.info("Analyst created");
            }
        } catch (Exception e) {
            log.error("Error creating professor. Message: {}", e.getMessage());
        }
    }

    private void createProfessorUser() {
        try {
            if (!userService.existsByEmail(professorEmail)) {
                User professor = userService.createUser(UserCreationDto.builder()
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
    }

    private void createAdminUser() {
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
            log.error("Error creating admin with credential '{}' '{}'. Error: {}", superAdminEmail, superAdminPassword, e.getMessage());
        }
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

    private void createResources() {
        createResourceByNameAndUrl("/*", "ALL");
        createResourceByNameAndUrl("/login", "LOGIN");
        createResourceByNameAndUrl("/logout", "LOGOUT");
        createResourceByNameAndUrl("/user", "USER");
        createResourceByNameAndUrl("/management", "MANAGEMENT");
        createResourceByNameAndUrl("/activity", "ACTIVITY");
        createResourceByNameAndUrl("/timeslot", "TIMESLOT");
        createResourceByNameAndUrl("/available-class", "AVAILABLE_CLASS");
        createResourceByNameAndUrl("/reservation", "RESERVATION");
    }

    private void createResourceByNameAndUrl(String resourceUrl, String resourceName) {
        try {
            if (!resourceService.existsByUrl(resourceUrl)) {
                resourceService.createResource(ResourceCreationDto.builder()
                        .name(resourceName)
                        .url(resourceUrl)
                        .build());
                log.info("Resource {} created.", resourceUrl);
            }
        } catch (Exception e) {
            log.error("Error creating resource {}. Error: {}.", resourceUrl, e.getMessage());
        }
    }

    private void createRoles() {
        try {
            createAdminRole();
        } catch (Exception e) {
            log.error("Error creating admin role. Error: {}.", e.getMessage());
        }
        try {
            createProfessorRole();
        } catch (Exception e) {
            log.error("Error creating professor role. Error: {}.", e.getMessage());
        }
        try {
            createAnalystRole();
        } catch (Exception e) {
            log.error("Error creating analyst role. Error: {}.", e.getMessage());
        }
        try {
            createUserRole();
        } catch (Exception e) {
            log.error("Error creating user role. Error: {}.", e.getMessage());
        }
    }

    private void createUserRole() {
        if (!roleService.existsByName("ROLE_USER")) {
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_USER")
                    .allowedResourcesIds(List.of(resourceService.findByName("LOGIN").getId(),
                            resourceService.findByName("LOGOUT").getId(),
                            resourceService.findByName("USER").getId(),
                            resourceService.findByName("ACTIVITY").getId()))
                    .build());
            log.info("User role created");
        }
    }

    private void createAnalystRole() {
        if (!roleService.existsByName("ROLE_ANALYST")) {

            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_ANALYST")
                    .allowedResourcesIds(List.of(resourceService.findByName("ALL").getId()))
                    .build());
            log.info("Analyst role created");

        }
    }

    private void createProfessorRole() {
        if (!roleService.existsByName("ROLE_PROFESSOR")) {

            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_PROFESSOR")
                    .allowedResourcesIds(List.of(resourceService.findByName("LOGIN").getId(),
                            resourceService.findByName("LOGOUT").getId(),
                            resourceService.findByName("ACTIVITY").getId(),
                            resourceService.findByName("USER").getId()))
                    .build());
            log.info("Professor role created");

        }
    }

    private void createAdminRole() {
        if (!roleService.existsByName("ROLE_ADMIN")) {

            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_ADMIN")
                    .allowedResourcesIds(List.of(resourceService.findByName("ALL").getId()))
                    .build());
            log.info("Admin role created");
        }
    }
}
