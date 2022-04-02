package com.sip.api.domains.role;

import com.sip.api.dtos.role.RoleDto;

import java.util.List;
import java.util.stream.Collectors;

public class RoleConverter {

    public static RoleDto entityToDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static List<RoleDto> entityToDto(List<Role> roles) {
        return roles.stream()
                .map(RoleConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public static Role dtoToEntity(RoleDto roleDto){
        return Role.builder()
                .name(roleDto.getName())
                .build();
    }
}
