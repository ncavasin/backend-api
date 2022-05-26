package com.sip.api.service;

import com.sip.api.domains.enums.UserStatus;
import com.sip.api.domains.resource.Resource;
import com.sip.api.domains.role.Role;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.dtos.user.UserPasswordDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.security.PasswordEncoder;
import com.sip.api.services.ResourceService;
import com.sip.api.services.RoleService;
import com.sip.api.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;


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

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Long phone = 123456789L;
    private final String userEmail = "ncavasin97@gmail.com";
    private final String firstName = "Nicolas";
    private final String lastName = "Cavasin";
    private Resource userResource;
    private Role userRole;
    private User savedUser;

    @Before
    @Transactional
    public void setUp() {
        String strongPassword = "password123!";
        savedUser = userService.createUser(UserCreationDto.builder()
                .dni(12345678)
                .password(strongPassword)
                .email(userEmail)
                .phone(phone)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(LocalDate.of(1997, 3, 26))
                .rolesNames(Collections.singletonList(roleService.findByName("ROLE_USER").getName()))
                .build());
    }

    @Test
    @Transactional
    public void shouldCreateUser() {
        Assert.assertEquals(userService.findById(savedUser.getId()), savedUser);
    }

    @Test
    @Transactional
    public void shouldNotCreateWhenWeakPassword() {
        Assert.assertThrows(BadRequestException.class, () ->
                userService.createUser(UserCreationDto.builder()
                        .dni(12345678)
                        .password("123")
                        .email(userEmail)
                        .phone(phone)
                        .firstName(firstName)
                        .lastName(lastName)
                        .birthDate(LocalDate.of(1997, 3, 26))
                        .rolesNames(Collections.singletonList(roleService.findByName("ROLE_USER").getName()))
                        .build()));
    }

    @Test
    @Transactional
    public void shouldNotCreateWhenDuplicatedEmail() {
        // Create another user with the same email than the saved User
        Assert.assertThrows(BadRequestException.class, () ->
                userService.createUser(UserCreationDto.builder()
                        .dni(98765431)
                        .password("anotherPassword!!!")
                        .email(savedUser.getEmail())
                        .phone(phone)
                        .firstName("Juan")
                        .lastName("Perez")
                        .birthDate(LocalDate.of(1997, 3, 26))
                        .rolesNames(Collections.singletonList(roleService.findByName("ROLE_USER").getName()))
                        .build()));
    }

    @Test
    @Transactional
    public void shouldUpdateEmail() {
        String updatedEmail = "updatedEmail@mail.com";
        Assert.assertNotEquals(savedUser.getEmail(), updatedEmail);

        final User updatedUser = userService.updateEmail(savedUser.getId(), UserEmailDto.builder()
                .email(updatedEmail)
                .build());
        Assert.assertEquals(updatedUser.getEmail(), updatedEmail);
    }

    @Test
    @Transactional
    public void shouldUpdatePassword() {
        String updatedPassword = "updatedPassword!";
        Assert.assertNotEquals(savedUser.getPassword(), updatedPassword);

        final User updatedUser = userService.updatePassword(savedUser.getId(), UserPasswordDto.builder()
                .password(updatedPassword)
                .build());
        Assert.assertTrue(passwordEncoder.encoder().matches(updatedPassword, updatedUser.getPassword()));
    }

    @Test
    @Transactional
    public void shouldDeactivateUser() {
        User foundUser = userService.findById(savedUser.getId());
        userService.deactivateUser(foundUser.getId());
        foundUser = userService.findById(savedUser.getId());
        Assert.assertEquals(foundUser.getStatus(), UserStatus.DEACTIVATED);
    }

    @Test
    @Transactional
    public void shouldDeleteUser() {
        User foundUser = userService.findById(savedUser.getId());
        userService.deleteUser(foundUser.getId());
        Assert.assertThrows(NotFoundException.class, () -> userService.findById(foundUser.getId()));
    }
}
