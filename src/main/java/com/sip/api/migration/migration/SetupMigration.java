package com.sip.api.migration.migration;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.timeslot.Timeslot;
import com.sip.api.domains.user.User;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.dtos.availableClass.AvailableClassCreationDto;
import com.sip.api.dtos.plan.PlanCreationDto;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.timeslot.TimeslotCreationDto;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.migration.StartUpMigration;
import com.sip.api.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetupMigration implements StartUpMigration {
    private final ResourceService resourceService;
    private final RoleService roleService;
    private final UserService userService;
    private final TimeslotService timeslotService;
    private final ActivityService activityService;
    private final AvailableClassService availableClassService;
    private final PlanService planService;

    @Value("${superadmin-email}")
    private String superAdminEmail;

    private final String professorEmail = "professor@mail.com";

    @Override
    public void run(ApplicationReadyEvent event) {
        createResources();
        createRoles();
        createUsers();
        createTimeslots();
        createActivities();
        createAvailableClasses();
        createPlans();
    }

    private void createPlans() {
        createPlan("Bronze", "Bronze plan", 1500D, 2);
        createPlan("Silver", "Silver plan", 2250D, 3);
        createPlan("Gold", "Gold plan", 2750D, 4);
        createPlan("Platinum", "Platinum plan", 3500D, 7);
    }

    private void createPlan(String name, String description, Double price, Integer activitiesLimit) {
        try {
            planService.createPlan(PlanCreationDto.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .activitiesLimit(activitiesLimit)
                    .build());
            log.info("Plan {} created.", name);
        } catch (BadRequestException e) {
            log.info("Creation skipped. Plan {} already exists.", name);
        }
    }

    private void createAvailableClasses() {
        createAvailableClass(1, 0);
        createAvailableClass(1, 1);
        createAvailableClass(1, 2);
        createAvailableClass(1, 3);
        createAvailableClass(1, 4);
        createAvailableClass(1, 5);
        createAvailableClass(2, 6);
        createAvailableClass(2, 7);
        createAvailableClass(2, 8);
        createAvailableClass(2, 9);
        createAvailableClass(2, 10);
        createAvailableClass(3, 11);
        createAvailableClass(3, 12);
        createAvailableClass(3, 13);
        createAvailableClass(3, 14);
        createAvailableClass(3, 15);
        createAvailableClass(3, 16);
        createAvailableClass(3, 17);
        createAvailableClass(3, 18);
        createAvailableClass(3, 19);
        createAvailableClass(3, 20);
    }

    private void createAvailableClass(int activityPosition, int timeslotPosition) {
        try {
            Activity activity = activityService.findAll().get(activityPosition);
            Timeslot timeslot = timeslotService.findAll().get(timeslotPosition);
            availableClassService.createAvailableClass(AvailableClassCreationDto.builder()
                    .activityId(activity.getId())
                    .timeslotId(timeslot.getId())
                    .build());
            log.info("AvailableClass {} from {} to {} on {} created.", activity.getName(), timeslot.getStartTime(), timeslot.getEndTime(), timeslot.getDayOfWeek());
        } catch (BadRequestException e) {
            log.warn("Creation skipped. AvailableClass already exists.");
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
            log.info("Activity {} created.", activityName);
        } catch (BadRequestException e) {
            log.info("Creation skipped. Activity {} already exists", activityName);
        }
    }

    private void createTimeslots() {
        for (int i = 7; i < 13; i++) {
            for (int dayAsNumber = 1; dayAsNumber < 6; dayAsNumber++) {
                LocalTime startTime = LocalTime.of(i, 15);
                LocalTime endTime = startTime.plusHours(1);
                DayOfWeek dayOfWeek = DayOfWeek.of(dayAsNumber);
                createTimeslot(startTime, endTime, dayOfWeek);
            }
        }
        for (int i = 16; i < 22; i++) {
            for (int dayAsNumber = 1; dayAsNumber < 6; dayAsNumber++) {
                LocalTime startTime = LocalTime.of(i, 15);
                LocalTime endTime = startTime.plusHours(1);
                DayOfWeek dayOfWeek = DayOfWeek.of(dayAsNumber);
                createTimeslot(startTime, endTime, dayOfWeek);
            }
        }
    }

    private void createTimeslot(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        try {
            timeslotService.createTimeslot(TimeslotCreationDto.builder()
                    .startTime(startTime)
                    .endTime(endTime)
                    .dayOfWeek(dayOfWeek)
                    .build());
            log.info("Timeslot from {} to {} on {} created.", startTime, endTime, dayOfWeek);
        } catch (BadRequestException e) {
            log.info("Creation skipped. Timeslot from {} to {} on {} already exists.", startTime, endTime, dayOfWeek);
        }
    }

    private void createUsers() {
        String analystMail = "analyst@mail.com";
        createUser(11111111, analystMail, Collections.singletonList("ROLE_ANALYST"));
        createUser(22222222, professorEmail, Collections.singletonList("ROLE_PROFESSOR"));
        createUser(33333333, superAdminEmail, Collections.singletonList("ROLE_ADMIN"));
        createUser(44444444, "user@user.com", Collections.singletonList("ROLE_USER"));
        createUser(55555555, "ncavasin97@gmail.com", Collections.singletonList("ROLE_USER"));
    }

    private void createUser(int dni, String mail, List<String> rolesNames) {
        try {
            User analyst = userService.createUser(UserCreationDto.builder()
                    .dni(dni)
                    .email(mail)
                    .password("12345678")
                    .rolesNames(rolesNames)
                    .build());
            userService.activateUser(analyst.getId());
            log.info("User '{}' created", mail);
        } catch (BadRequestException e) {
            log.info("Creation skipped. User '{}' already exists.", mail);
        }
    }

    private void createRoles() {
        createAdminRole();
        createProfessorRole();
        createAnalystRole();
        createUserRole();
    }

    private void createUserRole() {
        try {
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_USER")
                    .allowedResourcesIds(List.of(resourceService.findByName("LOGIN").getId(),
                            resourceService.findByName("LOGOUT").getId(),
                            resourceService.findByName("USER").getId(),
                            resourceService.findByName("ACTIVITY").getId()))
                    .build());
            log.info("User role created");
        } catch (BadRequestException e) {
            log.info("Creation skipped. ROLE_USER already exists.");
        }
    }

    private void createAnalystRole() {
        try {
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_ANALYST")
                    .allowedResourcesIds(List.of(resourceService.findByName("ALL").getId()))
                    .build());
            log.info("Analyst role created");
        } catch (BadRequestException e) {
            log.info("Creation skipped. ROLE_ANALYST already exists.");
        }
    }

    private void createProfessorRole() {
        try {
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_PROFESSOR")
                    .allowedResourcesIds(List.of(resourceService.findByName("LOGIN").getId(),
                            resourceService.findByName("LOGOUT").getId(),
                            resourceService.findByName("ACTIVITY").getId(),
                            resourceService.findByName("USER").getId()))
                    .build());
            log.info("Professor role created");
        } catch (BadRequestException e) {
            log.info("Creation skipped. ROLE_PROFESSOR already exists.");
        }
    }

    private void createAdminRole() {
        try {
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_ADMIN")
                    .allowedResourcesIds(List.of(resourceService.findByName("ALL").getId()))
                    .build());
            log.info("Admin role created");
        } catch (BadRequestException e) {
            log.info("Creation skipped. ROLE_ADMIN already exists.");
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
        createResourceByNameAndUrl("/plan", "PLAN");
        createResourceByNameAndUrl("/payment", "PAYMENT");
        createResourceByNameAndUrl("/subscription", "SUBSCRIPTION");
    }

    private void createResourceByNameAndUrl(String resourceUrl, String resourceName) {
        try {
            resourceService.createResource(ResourceCreationDto.builder()
                    .name(resourceName)
                    .url(resourceUrl)
                    .build());
            log.info("Resource {} created.", resourceUrl);
        } catch (BadRequestException e) {
            log.info("Creation skipped, resource already exists.");
        }
    }

    @Override
    public String getName() {
        return "Creation of Resource, Role and User";
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
