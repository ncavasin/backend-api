package com.sip.api;

import com.sip.api.domains.role.Role;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.services.RoleService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetSuperAdmin implements ApplicationRunner {
    @Value("${superadmin-email}")
    private String superAdminEmail;
    @Value("${superadmin-password}")
    private String superAdminPassword;

    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void run(ApplicationArguments args) {
        Role adminRole = roleService.createRole("SUPER_ADMIN");
        if(!userService.existsByEmail(superAdminEmail)) {
            userService.createUser(UserCreationDto.builder()
                    .dni(99999999)
                    .email(superAdminEmail)
                    .password(superAdminPassword)
                    .rolesIds(Collections.singletonList(adminRole.getId()))
                    .build());
            log.info("Super admin created");
        }
    }
}
