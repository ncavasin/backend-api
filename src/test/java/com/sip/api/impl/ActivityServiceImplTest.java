package com.sip.api.impl;

import com.sip.api.domains.activity.Activity;
import com.sip.api.domains.resource.Resource;
import com.sip.api.domains.role.Role;
import com.sip.api.domains.user.User;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.activity.ActivityCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.repositories.RoleRepository;
import com.sip.api.services.ActivityService;
import com.sip.api.services.UserService;
import com.sip.api.utils.mock.ResourceMocking;
import com.sip.api.utils.mock.RoleMocking;
import com.sip.api.utils.mock.UserMocking;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class ActivityServiceImplTest {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    private Activity savedActivity;
    private User professor;
    private final String name = "NEW ACTIVITY";
    private final Double basePrice = 2750.25D;
    private final int attendeesLimit = 6;

    @Before
    @Transactional
    public void setUp() {
        professor = generateProfessor();
        savedActivity = generateActivity(name, basePrice, professor, attendeesLimit);
    }

    private User generateProfessor() {
        // Generate resources
        Set<Resource> resources = Set.of(ResourceMocking.generateRawResourceWithParams("ALL", "/*"),
                ResourceMocking.generateRawResourceWithParams("ACTIVITY", "/activity"));

        // Generate roles
//        Set<Role> roles = roleRepository.saveAll(Set.of(RoleMocking.generateRawRoleWithParams("PROFESSOR", resources)));

//        UserMocking.getRawUserWithStatusActiveByParams(roles);
        return null;
    }

    @Test
    public void createActivity() {
        Assert.assertEquals(savedActivity, activityService.findById(savedActivity.getId()));
    }

    @Test
    public void createActivityWithSameName_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class, () -> generateActivity(name, basePrice, professor, attendeesLimit));
    }

    @Test
    public void createActivityWithNegativeAttendeesLimit_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class, () -> generateActivity(name, basePrice, professor, -1));
    }

    public void updateActivityBasePrice() {

    }

    public void updateActivityAttendeesLimit() {

    }

    public void deleteActivity() {

    }

    private Activity generateActivity(String name, Double basePrice, User professor, int attendeesLimit) {
        return activityService.createActivity(
                ActivityCreationDto.builder()
                        .name(name)
                        .basePrice(basePrice)
                        .professor(UserConverter.entityToDtoSlim(userService.findById(professor.getId())))
                        .attendeesLimit(attendeesLimit)
                        .build());
    }
}
