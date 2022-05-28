package com.sip.api.service;

import com.sip.api.domains.enums.UserStatus;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Long phone = 123456789L;
    private final String userEmail = "ncavasin97@gmail.com";
    private final String firstName = "Nicolas";
    private final String lastName = "Cavasin";
    private final String weakPassword = "weak";
    private User savedUser;
    private User updatableUser;
    private User deletableUser;

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
        updatableUser = userService.createUser(UserCreationDto.builder()
                .dni(222444)
                .password("updatablePASSWORD")
                .email("updatable@mail.com")
                .phone(phone)
                .firstName("updatable")
                .lastName("user")
                .birthDate(LocalDate.of(1945, 7, 1))
                .rolesNames(Collections.singletonList(roleService.findByName("ROLE_USER").getName()))
                .build());
        deletableUser = userService.createUser(UserCreationDto.builder()
                .dni(55555)
                .password("strongPassword")
                .email("deletable@mail.com")
                .phone(phone)
                .firstName("deletable")
                .lastName("user")
                .birthDate(LocalDate.of(1990, 5, 20))
                .rolesNames(Collections.singletonList(roleService.findByName("ROLE_USER").getName()))
                .build());
    }

    @Test
    @Transactional
    public void createUser() {
        Assert.assertEquals(userService.findById(savedUser.getId()), savedUser);
    }

    @Test
    @Transactional
    public void createUserWithWeakPassword_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class, () ->
                userService.createUser(UserCreationDto.builder()
                        .dni(12345678)
                        .password(weakPassword)
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
    public void createUserWithDuplicatedEmail_shouldThrowBadRequest() {
        // Create another user with the same email of saved User
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
    public void findUsersByRole() {
        final Role adminRole = roleService.findByName("ROLE_ADMIN");
        final List<User> users = userService.findAllUsersByRole(adminRole.getId());
        Assert.assertEquals(users.size(), 1);
    }

    @Test
    @Transactional
    public void updateEmail() {
        final String updatedEmail = "updatedEmail@mail.com";
        final User found = userService.findById(savedUser.getId());
        Assert.assertNotEquals(found.getEmail(), updatedEmail);

        final User updatedUser = userService.updateEmail(found.getId(), UserEmailDto.builder().email(updatedEmail).build());
        Assert.assertEquals(updatedUser.getEmail(), updatedEmail);
    }

    @Test
    @Transactional
    public void updateEmailWithDuplicated_shouldThrowBadRequest() {
        final User found = userService.findById(savedUser.getId());
        Assert.assertNotEquals(found.getEmail(), updatableUser.getEmail());

        Assert.assertThrows(BadRequestException.class, () -> userService.updateEmail(updatableUser.getId(),
                UserEmailDto.builder()
                        .email(savedUser.getEmail())
                        .build()));
    }

    @Test
    @Transactional
    public void updatePassword() {
        String updatedPassword = "updatedPassword!";
        final User found = userService.findById(savedUser.getId());
        Assert.assertNotEquals(found.getPassword(), updatedPassword);

        final User updatedUser = userService.updatePassword(found.getId(), UserPasswordDto.builder()
                .password(updatedPassword)
                .build());
        Assert.assertTrue(passwordEncoder.encoder().matches(updatedPassword, updatedUser.getPassword()));
    }

    @Test
    @Transactional
    public void updatePasswordWithWeakOne_shouldThrowBadRequest() {
        final User found = userService.findById(savedUser.getId());
        Assert.assertNotEquals(found.getPassword(), weakPassword);
        Assert.assertThrows(BadRequestException.class, () -> userService.updatePassword(found.getId(), UserPasswordDto.builder()
                .password(weakPassword)
                .build()));
    }

    @Test
    @Transactional
    public void activateUser() {
        final User foundUser = userService.findById(savedUser.getId());
        Assert.assertNotEquals(foundUser.getStatus(), UserStatus.ACTIVE);
        final User updated = userService.activateUser(foundUser.getId());
        Assert.assertEquals(updated.getStatus(), UserStatus.ACTIVE);
    }


    @Test
    @Transactional
    public void deactivateUser() {
        User foundUser = userService.findById(savedUser.getId());
        userService.deactivateUser(foundUser.getId());
        foundUser = userService.findById(savedUser.getId());
        Assert.assertEquals(foundUser.getStatus(), UserStatus.DEACTIVATED);
    }

    @Test
    @Transactional
    public void deleteUser() {
        final User foundUser = userService.findById(deletableUser.getId());
        userService.deleteUser(foundUser.getId());
        Assert.assertThrows(NotFoundException.class, () -> userService.findById(foundUser.getId()));
    }

    @Test
    @Transactional
    public void assignRoleToUser() {
        final Role adminRole = roleService.findByName("ROLE_ADMIN");
        final User found = userService.findById(savedUser.getId());
        Assert.assertFalse(found.getRoles().contains(adminRole));

        final User updated = userService.assignRoleToUserById(found.getId(), adminRole.getId());
        Assert.assertTrue(updated.getRoles().contains(adminRole));
    }

    @Test
    @Transactional
    public void removeRoleFromUser() {
        final Role userRole = roleService.findByName("ROLE_USER");
        final User found = userService.findById(savedUser.getId());
        Assert.assertTrue(found.getRoles().contains(userRole));

        final User updated = userService.removeRoleToUserById(found.getId(), userRole.getId());
        Assert.assertFalse(updated.getRoles().contains(userRole));
    }

    @Test
    @Transactional
    public void removeUnassignedRoleFromUser_shouldThrowBadRequest() {
        final Role adminRole = roleService.findByName("ROLE_ADMIN");
        final User found = userService.findById(savedUser.getId());
        Assert.assertThrows(BadRequestException.class, () -> userService.removeRoleToUserById(found.getId(), adminRole.getId()));
    }
}
