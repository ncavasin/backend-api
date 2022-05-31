package com.sip.api.migration.migration;

import com.sip.api.domains.user.User;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.migration.StartUpMigration;
import com.sip.api.services.ResourceService;
import com.sip.api.services.RoleService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMigration implements StartUpMigration {
    private final ResourceService resourceService;
    private final RoleService roleService;
    private final UserService userService;

    @Value("${superadmin-email}")
    private String superAdminEmail;
    @Value("${superadmin-password}")
    private String superAdminPassword;

    private final String professorEmail = "professor@mail.com";
    private final String analystMail = "analyst@mail.com";

    @Override
    public void run(ApplicationReadyEvent event) {
        createResources();
        createRoles();
        createUsers();
    }

    private void createUsers() {
        createUser(11111111, analystMail, Collections.singletonList("ROLE_ANALYST"));
        createUser(22222222, professorEmail, Collections.singletonList("ROLE_PROFESSOR"));
        createUser(33333333, superAdminEmail, Collections.singletonList("ROLE_ADMIN"));
        createUser(44444444, "user@user.com", Collections.singletonList("ROLE_USER"));
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
