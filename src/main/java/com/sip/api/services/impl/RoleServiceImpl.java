package com.sip.api.services.impl;

import com.sip.api.domains.role.Role;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.RoleRepository;
import com.sip.api.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findById(String roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new NotFoundException("Role not found"));
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new NotFoundException(String.format("Role %s not found", name)));
    }

    @Override
    public Role createRole(String name) {
        return roleRepository.save(new Role(checkNameExistence(name)));
    }

    @Override
    public Role updateName(String roleId, String newName) {
        Role role = findById(roleId);
        role.setName(checkNameExistence(newName));
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(String roleId) {
        if (!roleRepository.existsById(roleId)) throw new NotFoundException("Role not found");
        roleRepository.deleteById(roleId);
    }

    private String checkNameExistence(String name) {
        if (roleRepository.existsByName(name))
            throw new BadRequestException(String.format("Role %s already exists", name));
        return name;
    }

}
