package com.sip.api.services;

import com.sip.api.domains.Role;

public interface RoleService {

    Role findById(String roleId);

    Role findByName(String roleName);

    Role createRole(String roleName);

    Role updateName(String roleId, String roleName);

    void deleteRole(String roleId);
}
