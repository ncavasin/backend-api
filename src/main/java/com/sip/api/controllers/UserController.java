package com.sip.api.controllers;

import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.user.UserDniDto;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.dtos.user.UserPasswordDto;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public UserDto findByDni(@RequestBody @Valid UserDniDto userDniDto) {
        return UserConverter.entityToDto(userService.findByDni(userDniDto));
    }

    @GetMapping("/email")
    public UserDto findByEmail(@RequestBody @Valid UserEmailDto userEmailDto) {
        return UserConverter.entityToDto(userService.findByEmail(userEmailDto));
    }

    @PutMapping("/email/{userId}")
    public UserDto updateEmail(@PathVariable("userId") String userId, @Valid @RequestBody UserEmailDto userEmailDto) {
        return UserConverter.entityToDto(userService.updateEmail(userId, userEmailDto));
    }

    @PutMapping("/password/{userId}")
    public UserDto updatePassword(@PathVariable("userId") String userId, @Valid @RequestBody UserPasswordDto userPasswordDto) {
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
