package com.sip.api.controllers;

import com.sip.api.dtos.user.UserDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<UserDto> findAll() {
        return UserDto.entityToDto(userService.findAll());
    }

    @GetMapping("/{userId}")
    public UserDto findUser(@PathVariable("userId") String userId) {
        return UserDto.entityToDto(userService.findById(userId));
    }

    @PostMapping("")
    public UserDto addUser(@Validated @RequestBody UserDto userCreationDto) {
        return UserDto.entityToDto(userService.createUser(userCreationDto));
    }

    @PutMapping("/email/{userId}")
    public UserDto updateEmail(@PathVariable("userId") String userId, @Validated @RequestBody UserEmailDto userEmailDto) {
        return UserDto.entityToDto(userService.updateEmail(userId, userEmailDto));
    }

    @PutMapping("/password/{userId}")
    public UserDto updatePassword(@PathVariable("userId") String userId, @Validated @RequestBody UserPasswordDto userPasswordDto) {
        return UserDto.entityToDto(userService.updatePassword(userId, userPasswordDto));
    }

    @PutMapping("/activate/{userId}")
    public UserDto activateUser(@PathVariable("userId") String userId) {
        return UserDto.entityToDto(userService.activateUser(userId));
    }

    @DeleteMapping("/deactivate/{userId}")
    public void deactivateUser(@PathVariable("userId") String userId) {
        userService.deactivateUser(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
    }

}
