package com.sip.api.controller;

import com.sip.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<UserDto> findAll(){
        return UserDto.from(userService.findAll());
    }

    @GetMapping("/{userId}")
    public UserDto findUser(@PathVariable("userId") String userId) {
        return UserDto.from(userService.findById(userId));
    }

    @PostMapping("")
    public UserDto addUser(@Validated @RequestBody UserCreationDto userCreationDto) {
        return UserDto.from(userService.createUser(userCreationDto));
    }

    @PutMapping("/username/{userId}")
    public UserDto updateUsername(@PathVariable("userId") String userId, @Validated @RequestBody UserUsernameDto userUsernameDto) {
        return UserDto.from(userService.updatUsername(userId, userUsernameDto));
    }

    @PutMapping("/email/{userId}")
    public UserDto updateEmail(@PathVariable("userId") String userId, @Validated @RequestBody UserEmailDto userEmailDto) {
        return UserDto.from(userService.updateEmail(userId, userEmailDto));
    }

    @PutMapping("/password/{userId}")
    public UserDto updatePassword(@PathVariable("userId") String userId, @Validated @RequestBody UserPasswordDto userPasswordDto) {
        return UserDto.from(userService.updatePassword(userId, userPasswordDto));
    }

    @PutMapping("/activate/{userId}")
    public UserDto activateUser(@PathVariable("userId") String userId) {
        return UserDto.from(userService.activateUser(userId));
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
