package com.sip.api.services;

import com.sip.api.domains.enums.UserStatus;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.dtos.user.UserPasswordDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    private User savedUser;

    @BeforeEach
    private void setUp() {

        // Create the roles
/*        adminRole = roleService.createRole(RoleCreationDto.builder()
        .name("ADMIN_ROLE")
        .allowedResourcesIds(Collections.singletonList(resourcesMock.getAllResource().getId()))
        .build());*/
//        userRole = roleService.createRole(RoleCreationDto.builder()
//                .name("USER_ROLE")
//                .allowedResourcesIds()
//                .build());
//        professorRole = roleService.createRole(RoleCreationDto.builder()
//                .name("PROFESSOR_ROLE")
//                .allowedResourcesIds()
//                .build());
//        analystRole = roleService.createRole(RoleCreationDto.builder()
//                .name("ANALYST_ROLE")
//                .allowedResourcesIds()
//                .build());

        savedUser = createUser(12345678, "password123!", "ncavasin97@gmail.com", 23456781,
                "Nicolás", "Cavasin", 25, null);
    }

    @Test
    @Transactional
    public void shouldCreateUser() {
        Assert.assertEquals(userService.findById(savedUser.getId()), savedUser);
    }

    @Test
    public void shouldNotCreateWhenWeakPassword() {
        Assert.assertThrows(BadRequestException.class, () -> createUser(12345678, "123",
                "ncavasin97@gmail.com", 23456781,
                "Nicolás", "Cavasin", 25, null));
    }

    @Test
    public void shouldNotCreateWhenDuplicatedEmail() {
        // Create another user with the same email than the saved User
        Assert.assertThrows(BadRequestException.class, () -> createUser(87654321, "securepassword1!", savedUser.getEmail(), 999944,
                "Juan", "Perez", 45, null));
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

    private User createUser(int dni, String password, String email, int phone, String firstName, String lastName, int age, List<String> rolesNames) {
        return userService.createUser(UserCreationDto.builder()
                .dni(dni)
                .password(password)
                .email(email)
                .phone(phone)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .rolesNames(rolesNames)
                .build());
    }
}
