package com.sip.api.services.impl;

import com.sip.api.domains.resource.Resource;
import com.sip.api.domains.role.Role;
import com.sip.api.dtos.role.RoleCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.RoleRepository;
import com.sip.api.services.ResourceService;
import com.sip.api.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ResourceService resourceService;

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
        return roleRepository.findByName(name).orElseThrow(() -> new NotFoundException(String.format("Role '%s' not found", name)));
    }

    @Override
    public Role createRole(RoleCreationDto roleCreationDto) {
        if (roleRepository.existsByName(roleCreationDto.getName()))
            throw new BadRequestException(String.format("Role with name %s already exists!", roleCreationDto.getName()));
        Set<Resource> allowedResources = roleCreationDto.getAllowedResourcesIds() == null ? new LinkedHashSet<>() :
                roleCreationDto.getAllowedResourcesIds()
                        .stream()
                        .map(resourceService::findById)
                        .collect(Collectors.toSet());
        return roleRepository.save(new Role(checkNameExistence(roleCreationDto.getName()), allowedResources));
    }

    @Override
    public Role addResourceToRole(String resourceId, String roleId) {
        Role role = findById(roleId);
        Resource newResource = resourceService.findById(resourceId);
        if (role.getAllowedResources().contains(newResource))
            throw new BadRequestException(String.format("Resource '%s' already exists in role", newResource.getName()));
        Set<Resource> allowedResources = role.getAllowedResources();
        allowedResources.add(newResource);
        role.setAllowedResources(allowedResources);
        return roleRepository.save(role);
    }

    @Override
    public Role removeResourceFromRole(String resourceId, String roleId) {
        Role role = findById(roleId);
        Resource resourceToRemove = resourceService.findById(resourceId);
        if (!role.getAllowedResources().contains(resourceToRemove))
            throw new BadRequestException(String.format("Resource '%s' does not exist in role", resourceToRemove.getName()));
        role.setAllowedResources(role.getAllowedResources()
                .stream()
                .filter(resource -> !resource.getId().equals(resourceId))
                .collect(Collectors.toSet()));
        return roleRepository.save(role);
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
            throw new BadRequestException(String.format("Role '%s' already exists", name));
        return name;
    }
}
