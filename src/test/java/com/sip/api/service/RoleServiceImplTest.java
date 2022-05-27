package com.sip.api.service;

import com.sip.api.domains.role.Role;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.services.ResourceService;
import com.sip.api.services.RoleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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

    private Role deletableRole;
    private final ThreadLocal<String> newRoleName = ThreadLocal.withInitial(() -> "newRole");

    @Before
    @Transactional
    public void setUp() {
        List<String> savedRoleResourcesIds = List.of(
                resourceService.findByName("USER").getId(),
                resourceService.findByName("ACTIVITY").getId());
        savedRole = generateRole(newRoleName.get(), savedRoleResourcesIds);
        deletableRole = generateRole("deletableRole", savedRoleResourcesIds);
    }

    @Test
    @Transactional
    public void createRole() {
        Assert.assertEquals(roleService.findById(savedRole.getId()), savedRole);
    }

    @Test
    @Transactional
    public void createRoleWithNoResources() {
        final Role noResourceRole = generateRole("roleWithNoResources", Collections.emptyList());
        Assert.assertEquals(roleService.findById(noResourceRole.getId()), noResourceRole);
        Assert.assertTrue(noResourceRole.getAllowedResources().isEmpty());
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
        final Role found = roleService.findById(deletableRole.getId());
        Assert.assertEquals(found, deletableRole);

        roleService.deleteRoleById(found.getId());
        Assert.assertThrows(NotFoundException.class, () -> roleService.findById(found.getId()));
    }

    @Test
    @Transactional
    public void deleteNonExistentRole_shouldThrowBadRequest() {
        Assert.assertThrows(NotFoundException.class, () -> roleService.deleteRoleById("NON_EXISTENT_ROLE_ID"));
    }

    @Test
    @Transactional
    public void deleteRoleByName() {
        final Role found = roleService.findById(savedRole.getId());
        Assert.assertEquals(found, savedRole);

        roleService.deleteRoleByName(found.getName());
        Assert.assertThrows(NotFoundException.class, () -> roleService.findByName(found.getName()));
    }

    @Test
    @Transactional
    public void deleteRoleByNonExistentName_shouldThrowBadRequest() {
        Assert.assertThrows(NotFoundException.class, () -> roleService.deleteRoleByName("NON_EXISTENT_ROLE_NAME"));

    }

    private Role generateRole(String name, List<String> allowedResourcesIds) {
        return roleService.createRole(RoleCreationDto.builder()
                .name(name)
                .allowedResourcesIds(allowedResourcesIds)
                .build());
    }
}
