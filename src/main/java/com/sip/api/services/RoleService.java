package com.sip.api.services;

import com.sip.api.domains.role.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(String roleId);

    Role findByName(String name);

    Role createRole(String name);

    void deleteRoleById(String roleId);

    void deleteRoleByName(String name);

    boolean existsByName(String roleName);
}
