package com.sip.api.controllers;

import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
public class ManagementController {
    private final UserService userService;

    @PutMapping("/assign-role/{userId}/{roleId}")
    public UserDto assignRoleToUserById(@PathVariable("userId") String userId, @PathVariable("roleId") String roleId) {
        return UserConverter.entityToDto(userService.assignRoleToUserById(userId, roleId));
    }


}
