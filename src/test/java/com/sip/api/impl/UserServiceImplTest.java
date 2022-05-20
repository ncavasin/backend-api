package com.sip.api.impl;

import com.sip.api.domains.enums.UserStatus;
import com.sip.api.domains.resource.Resource;
import com.sip.api.domains.role.Role;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.RoleCreationDto;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.dtos.user.UserPasswordDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.services.ResourceService;
import com.sip.api.services.RoleService;
import com.sip.api.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    private Resource userResource;
    private Role userRole;
    private User savedUser;

    @BeforeEach
    private void setUp() {
        userResource = resourceService.createResource(ResourceCreationDto.builder()
                .name("USER")
                .url("/user")
                .build());
        userRole = roleService.createRole(RoleCreationDto.builder()
                .name("USER_ROLE")
                .allowedResourcesIds(Collections.singletonList(userResource.getId()))
                .build());
        savedUser = createUser(12345678, "password123!", "ncavasin97@gmail.com", 23456781L,
                "Nicolás", "Cavasin", LocalDate.of(1997, 03, 26), null);
    }

    @Test
    @Transactional
    public void shouldCreateUser() {
        Assert.assertEquals(userService.findById(savedUser.getId()), savedUser);
    }

    @Test
    public void shouldNotCreateWhenWeakPassword() {
        Assert.assertThrows(BadRequestException.class, () -> createUser(12345678, "123",
                "ncavasin97@gmail.com", 23456781L,
                "Nicolás", "Cavasin",  LocalDate.of(1997, 03, 26), null));
    }

    @Test
    public void shouldNotCreateWhenDuplicatedEmail() {
        // Create another user with the same email than the saved User
        Assert.assertThrows(BadRequestException.class, () -> createUser(87654321, "securepassword1!", savedUser.getEmail(), 999944L,
                "Juan", "Perez",  LocalDate.of(1997, 03, 26), null));
    }

    @Test
    public void shouldUpdateEmail() {
        String updatedEmail = "updatedEmail@mail.com";
        Assert.assertNotEquals(savedUser.getEmail(), updatedEmail);

        final User updatedUser = userService.updateEmail(savedUser.getId(), UserEmailDto.builder()
                .email(updatedEmail)
                .build());
        Assert.assertEquals(updatedUser.getEmail(), updatedEmail);
    }

    @Test
    public void shouldUpdatePassword() {
        String updatedPassword = "updatedPassword!";
        Assert.assertNotEquals(savedUser.getPassword(), updatedPassword);

        final User updatedUser = userService.updatePassword(savedUser.getId(), UserPasswordDto.builder()
                .password(updatedPassword)
                .build());
        Assert.assertEquals(updatedUser.getPassword(), updatedPassword);
    }

    @Test
    public void shouldDeactivateUser() {
        User foundUser = userService.findById(savedUser.getId());
        Assert.assertEquals(foundUser.getStatus(), UserStatus.ACTIVE);

        userService.deactivateUser(foundUser.getId());
        foundUser = userService.findById(savedUser.getId());
        Assert.assertEquals(foundUser.getStatus(), UserStatus.INACTIVE);
    }

    @Test
    public void shouldDeleteUser() {
        User foundUser = userService.findById(savedUser.getId());
        userService.deleteUser(foundUser.getId());
        Assert.assertThrows(NotFoundException.class, () -> userService.findById(foundUser.getId()));
    }

    private User createUser(int dni, String password, String email, Long phone, String firstName, String lastName, LocalDate birthDate, List<String> rolesNames) {
        return userService.createUser(UserCreationDto.builder()
                .dni(dni)
                .password(password)
                .email(email)
                .phone(phone)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .rolesNames(rolesNames)
                .build());
    }
}
