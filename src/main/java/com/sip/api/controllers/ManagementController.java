package com.sip.api.controllers;

import com.sip.api.domains.role.RoleConverter;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.role.RoleDto;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.services.RoleService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
public class ManagementController {
    private final UserService userService;
    private final RoleService roleService;

    @PutMapping("/assign-role-to-user/{userId}/{roleId}")
    public UserDto assignRoleToUserById(@PathVariable("userId") String userId, @PathVariable("roleId") String roleId) {
        return UserConverter.entityToDto(userService.assignRoleToUserById(userId, roleId));
    }

    @PutMapping("/remove-role-from-user/{userId}/{roleId}")
    public UserDto removeRoleToUserById(@PathVariable("userId") String userId, @PathVariable("roleId") String roleId) {
        return UserConverter.entityToDto(userService.removeRoleToUserById(userId, roleId));
    }

    @PutMapping("/assign-resource-to-role/{resourceId}/{roleId}")
    public RoleDto assignResourceToRole(@PathVariable("resourceId") String resourceId, @PathVariable("roleId") String roleId) {
        return RoleConverter.entityToDto(roleService.addResourceToRole(resourceId, roleId));
    }

    @PutMapping("/remove-resource-from-role/{resourceId}/{roleId}")
    public RoleDto removeResourceFromRole(@PathVariable("resourceId") String resourceId, @PathVariable("roleId") String roleId) {
        return RoleConverter.entityToDto(roleService.removeResourceFromRole(resourceId, roleId));
    }

    @PostMapping("/add-user-with-roles")
    public UserDto createUserWithRoles(@RequestBody @Valid UserCreationDto userCreationDto) {
        return UserConverter.entityToDto(userService.createUser(userCreationDto));
    }
}
