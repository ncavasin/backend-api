package com.sip.api.controllers;

import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.user.*;
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
        return UserConverter.entityToDto(userService.findAll());
    }

    @GetMapping("/{userId}")
    public UserDto findById(@PathVariable("userId") String userId) {
        return UserConverter.entityToDto(userService.findById(userId));
    }

    @GetMapping("/dni")
    public UserDto findByDni(@RequestBody @Validated UserDniDto userDniDto){
        return UserConverter.entityToDto(userService.findByDni(userDniDto));
    }

    @GetMapping("/email")
    public UserDto findByEmail(@RequestBody @Validated UserEmailDto userEmailDto) {
        return UserConverter.entityToDto(userService.findByEmail(userEmailDto));
    }

    @PutMapping("/email/{userId}")
    public UserDto updateEmail(@PathVariable("userId") String userId, @Validated @RequestBody UserEmailDto userEmailDto) {
        return UserConverter.entityToDto(userService.updateEmail(userId, userEmailDto));
    }

    @PutMapping("/password/{userId}")
    public UserDto updatePassword(@PathVariable("userId") String userId, @Validated @RequestBody UserPasswordDto userPasswordDto) {
        return UserConverter.entityToDto(userService.updatePassword(userId, userPasswordDto));
    }

    @PutMapping("/activate/{userId}")
    public UserDto activateUser(@PathVariable("userId") String userId) {
        return UserConverter.entityToDto(userService.activateUser(userId));
    }

    @PutMapping("/deactivate/{userId}")
    public void deactivateUser(@PathVariable("userId") String userId) {
        userService.deactivateUser(userId);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
    }

}
