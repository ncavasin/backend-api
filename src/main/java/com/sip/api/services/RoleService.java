package com.sip.api.services;

import com.sip.api.domains.role.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(String roleId);

    Role findByName(String name);

    Role createRole(String name);

    Role updateName(String roleId, String newName);

    void deleteRole(String roleId);
}
