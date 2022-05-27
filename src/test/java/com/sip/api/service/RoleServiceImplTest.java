package com.sip.api.service;

import com.sip.api.domains.resource.Resource;
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
    private List<String> savedRoleResourcesIds;

    @Before
    @Transactional
    public void setUp() {
        savedRoleResourcesIds = List.of(
                resourceService.findByName("USER").getId(),
                resourceService.findByName("ACTIVITY").getId());
        String newRoleName = "newRole";
        savedRole = generateRole(newRoleName, savedRoleResourcesIds);
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
        final Resource newResource = resourceService.findByName("MANAGEMENT");
        Assert.assertFalse(savedRole.getAllowedResources().contains(newResource));

        final Role updated = roleService.addResourceToRole(newResource.getId(), savedRole.getId());
        Assert.assertTrue(updated.getAllowedResources().contains(newResource));
    }

    @Test
    @Transactional
    public void addNonExistingResourceToRole_shouldThrowBadRequest() {
        Assert.assertThrows(NotFoundException.class, () -> roleService.addResourceToRole("NON_EXISTENT_RESOURCE_ID", savedRole.getId()));
    }

    @Test
    @Transactional
    public void removeResourceFromRole() {
        final Resource toRemoveResource = resourceService.findById(savedRoleResourcesIds.get(0));
        Assert.assertTrue(savedRole.getAllowedResources().contains(toRemoveResource));

        final Role updated = roleService.removeResourceFromRole(toRemoveResource.getId(), savedRole.getId());
        Assert.assertFalse(updated.getAllowedResources().contains(toRemoveResource));
    }

    @Test
    @Transactional
    public void removeNonExistingResourceFromRole_shouldThrowBadRequest() {
        Assert.assertThrows(NotFoundException.class, () -> roleService.removeResourceFromRole("NON_EXISTENT_RESOURCE_ID", savedRole.getId()));
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
