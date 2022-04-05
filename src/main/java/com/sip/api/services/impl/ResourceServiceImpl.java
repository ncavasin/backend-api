package com.sip.api.services.impl;

import com.sip.api.domains.resource.Resource;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.resource.ResourceDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.ResourceRepository;
import com.sip.api.services.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;

    @Override
    public List<Resource> findAll() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource findById(String resourceId) {
        return resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("Resource not found"));
    }

    @Override
    public Resource findByName(String resourceName) {
        return resourceRepository.findByName(resourceName).orElseThrow(() -> new NotFoundException("Resource not found"));
    }

    @Override
    public Resource addResource(ResourceCreationDto resourceCreationDto) {
        checkExistence(resourceCreationDto.getName());
        return resourceRepository.save(Resource.builder()
                .name(resourceCreationDto.getName())
                .url(resourceCreationDto.getUrl())
                .build());
    }

    @Override
    public Resource updateResource(ResourceDto resourceDto) {
        Resource resource = findById(resourceDto.getId());
        checkExistence(resourceDto.getName());
        resource.setName(resourceDto.getName());
        resource.setUrl(resourceDto.getUrl());
        return resourceRepository.save(resource);
    }

    @Override
    public void deleteById(String resourceId) {
        if (!resourceRepository.existsById(resourceId)) throw new NotFoundException("Resource not found");
        resourceRepository.deleteById(resourceId);
    }

    @Override
    public void deleteByName(String resourceName) {
        Resource resource = findByName(resourceName);
        resourceRepository.deleteById(resource.getId());
    }

    private void checkExistence(String name) {
        if (resourceRepository.existsByName(name)) throw new BadRequestException("Resource already exists");
    }
}
