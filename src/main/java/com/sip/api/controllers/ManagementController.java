package com.sip.api.controllers;

import com.sip.api.domains.Role;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.services.RoleService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
public class ManagementController {
    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/role")
    public Role addRole(@RequestBody @Validated String roleName) {
        return roleService. createRole(roleName);
    }

    @PostMapping("/add-professor")
    public UserDto addProfessor(@RequestBody @Validated UserCreationDto userCreationDto) {
        return UserConverter.entityToDto(userService.createUser(userCreationDto));
    }

}
