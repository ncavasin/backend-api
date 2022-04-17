package com.sip.api;

import com.sip.api.domains.resource.Resource;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.services.ResourceService;
import com.sip.api.services.RoleService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasicSetup implements ApplicationRunner {
    private final UserService userService;
    private final RoleService roleService;
    private final ResourceService resourceService;
    private final List<Resource> resourceList;
    @Value("${superadmin-email}")
    private String superAdminEmail;
    @Value("${superadmin-password}")
    private String superAdminPassword;

    @Override
    public void run(ApplicationArguments args) {
        try {
            createResources();
            createRoles();

            if (!userService.existsByEmail(superAdminEmail)) {
                User user =  userService.createUser(UserCreationDto.builder()
                        .dni(99999999)
                        .email(superAdminEmail)
                        .password(superAdminPassword)
                        .rolesNames(Collections.singletonList("ROLE_ADMIN"))
                        .build());
                userService.activateUser(user.getId());
                log.info("Admin role created");
            }
        } catch (Exception e) {
            log.error("Something went wrong while setting up application!. Error: {}", e.getMessage());
        }
    }

    private void createResources() {
        if (!resourceService.existsByUrl("/*"))
            resourceList.add(resourceService.createResource(ResourceCreationDto.builder()
                    .name("ALL")
                    .url("/*")
                    .build()));
        if (!resourceService.existsByUrl("/user"))
            resourceList.add(resourceService.createResource(ResourceCreationDto.builder()
                    .name("USERS")
                    .url("/user")
                    .build()));
        if (!resourceService.existsByUrl("/management"))
            resourceList.add(resourceService.createResource(ResourceCreationDto.builder()
                    .name("MANAGEMENT")
                    .url("/management")
                    .build()));
        if (!resourceService.existsByUrl("/login"))
            resourceList.add(resourceService.createResource(ResourceCreationDto.builder()
                    .name("LOGIN")
                    .url("/login")
                    .build()));
        if (!resourceService.existsByUrl("/logout"))
            resourceList.add(resourceService.createResource(ResourceCreationDto.builder()
                    .name("LOGOUT")
                    .url("/logout")
                    .build()));
    }

    private void createRoles() {
        if (!roleService.existsByName("ROLE_ADMIN"))
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_ADMIN")
                    .allowedResourcesIds(Collections.singletonList(resourceList.get(0).getId()))
                    .build());
        if (!roleService.existsByName("ROLE_USER"))
            roleService.createRole(RoleCreationDto
                    .builder()
                    .name("ROLE_USER")
//                    .allowedResourcesIds(resourceList
//                            .stream()
//                            .map(AbstractEntity::getId)
//                            .filter(id -> !id.equals(resourceService.findByName("ALL").getId()))
//                            .collect(Collectors.toList()))
                    .allowedResourcesIds(List.of(resourceList.get(3).getId(), resourceList.get(4).getId()))
                    .build());
    }
}
