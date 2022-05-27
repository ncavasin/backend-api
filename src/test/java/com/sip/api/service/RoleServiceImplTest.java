package com.sip.api.service;

import com.sip.api.domains.role.Role;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.services.ResourceService;
import com.sip.api.services.RoleService;
import org.junit.Before;
import org.junit.Test;
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

    @Test
    @Transactional
    public void createRole() {

    }

    @Test
    @Transactional
    public void updateRoleName() {

    }

    @Test
    @Transactional
    public void addResourceToRole() {

    }

    @Test
    @Transactional
    public void addNonExistingResourceToRole_shouldThrowBadRequest() {

    }

    @Test
    @Transactional
    public void removeResourceFromRole() {

    }

    @Test
    @Transactional
    public void removeNonExistingResourceFromRole_shouldThrowBadRequest() {

    }

    @Test
    @Transactional
    public void deleteRole() {

    }

    @Test
    @Transactional
    public void deleteRoleByName() {

    }

    private Role generateRole(String name, List<String> allowedResourcesIds) {
        return roleService.createRole(RoleCreationDto.builder()
                .name(name)
                .allowedResourcesIds(allowedResourcesIds)
                .build());
    }
}
