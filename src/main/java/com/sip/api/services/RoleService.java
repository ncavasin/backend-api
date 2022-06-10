package com.sip.api.services;

import com.sip.api.domains.role.Role;
import com.sip.api.dtos.role.RoleCreationDto;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(String roleId);

    Role findByName(String name);

    Role createRole(RoleCreationDto roleCreationDto);

    Role addResourceToRole(String resourceId, String roleId);

    Role removeResourceFromRole(String resourceId, String roleId);

    void deleteRoleById(String roleId);

    void deleteRoleByName(String name);

    boolean existsByName(String roleName);
}
