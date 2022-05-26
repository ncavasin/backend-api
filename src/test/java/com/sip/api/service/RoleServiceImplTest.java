package com.sip.api.service;

import com.sip.api.domains.role.Role;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.services.ResourceService;
import com.sip.api.services.RoleService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class RoleServiceImplTest {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;
    private Role savedRole;
    private final String newRoleName = "newRole";
    private List<String> savedRoleRelatedResourcesIds;

    @Before
    @Transactional
    public void setUp() {
        savedRoleRelatedResourcesIds = List.of(
                resourceService.findByName("USER").getId(),
                resourceService.findByName("ACTIVITY").getId());
        savedRole = generateRole(newRoleName, savedRoleRelatedResourcesIds);
    }

    public void createRole() {

    }

    public void updateRoleName() {

    }

    public void addResourceToRole() {

    }

    public void addNonExistingResourceToRole_shouldThrowBadRequest() {

    }

    public void removeResourceFromRole() {

    }

    public void removeNonExistingResourceFromRole_shouldThrowBadRequest() {

    }

    public void deleteRole() {

    }

    public void deleteRoleByName() {

    }

    private Role generateRole(String name, List<String> allowedResourcesIds) {
        return roleService.createRole(RoleCreationDto.builder()
                .name(name)
                .allowedResourcesIds(allowedResourcesIds)
                .build());
    }
}
