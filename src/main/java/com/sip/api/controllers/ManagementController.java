package com.sip.api.controllers;

import com.sip.api.domains.role.RoleConverter;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.role.RoleDto;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.services.RoleService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
public class ManagementController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/role")
    public RoleDto getRole(@RequestBody @Validated RoleDto roleDto) {
        return RoleConverter.entityToDto(roleService.findByName(roleDto.getName()));
    }

    @GetMapping("/role")
    public List<RoleDto> getAll(){
        return RoleConverter.entityToDto(roleService.findAll());
    }

    @PostMapping("/role")
    public RoleDto addRole(@RequestBody @Validated RoleDto roleDto) {
        return RoleConverter.entityToDto(roleService. createRole(roleDto.getName()));
    }

    @PutMapping("/role")
    public RoleDto updateRoleName(@RequestBody @Validated RoleDto roleDto) {
        return RoleConverter.entityToDto(roleService.updateName(roleDto.getId(), roleDto.getName()));
    }

    @DeleteMapping
    public void deleteRole(@RequestBody @Validated RoleDto roleDto) {
        roleService.deleteRole(roleDto.getId());
    }



    @PostMapping("/add-professor")
    public UserDto addProfessor(@RequestBody @Validated UserCreationDto userCreationDto) {
        return UserConverter.entityToDto(userService.createUser(userCreationDto));
    }

}
