package com.sip.api.controllers;

import com.sip.api.domains.role.RoleConverter;
import com.sip.api.dtos.role.RoleDto;
import com.sip.api.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/management/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public RoleDto getRoleByName(@RequestBody @Valid RoleDto roleDto) {
        return RoleConverter.entityToDto(roleService.findByName(roleDto.getName()));
    }

    @GetMapping("{roleId}")
    public RoleDto getRoleById(@PathVariable("roleId") String roleId) {
        return RoleConverter.entityToDto(roleService.findById(roleId));
    }

    @GetMapping("/all")
    public List<RoleDto> getAll() {
        return RoleConverter.entityToDto(roleService.findAll());
    }

    @PostMapping
    public RoleDto addRole(@RequestBody @Valid RoleDto roleDto) {
        return RoleConverter.entityToDto(roleService.createRole(roleDto.getName()));
    }

    @DeleteMapping("/{roleId}")
    public void deleteRoleById(@PathVariable("roleId") String roleId) {
        roleService.deleteRoleById(roleId);
    }

    @DeleteMapping
    public void deleteRoleByName(@RequestBody @Valid RoleDto roleDto) {
        roleService.deleteRoleByName(roleDto.getName());
    }
}
