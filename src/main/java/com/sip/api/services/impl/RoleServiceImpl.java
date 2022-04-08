package com.sip.api.services.impl;

import com.sip.api.domains.role.Role;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.RoleRepository;
import com.sip.api.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

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
        return roleRepository.save(new Role(checkNameExistence(name), new HashSet<>()));
    }

    @Override
    public void deleteRoleById(String roleId) {
        if (!roleRepository.existsById(roleId)) throw new NotFoundException("Role not found");
        roleRepository.deleteById(roleId);
    }

    @Override
    public void deleteRoleByName(String name) {
        Role role = findByName(name);
        roleRepository.delete(role);
    }

    @Override
    public boolean existsByName(String roleName) {
        return roleRepository.existsByName(roleName);
    }

    private String checkNameExistence(String name) {
        if (roleRepository.existsByName(name))
            throw new BadRequestException(String.format("Role %s already exists", name));
        return name;
    }

}
