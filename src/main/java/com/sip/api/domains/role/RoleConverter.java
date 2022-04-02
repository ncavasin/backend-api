package com.sip.api.domains.role;

import com.sip.api.dtos.role.RoleDto;

public class RoleConverter {

    public static RoleDto entityToDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static Role dtoToEntity(RoleDto roleDto){
        return Role.builder()
                .name(roleDto.getName())
                .build();
    }
}
