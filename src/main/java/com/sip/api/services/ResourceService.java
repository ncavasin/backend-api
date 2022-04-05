package com.sip.api.services;

import com.sip.api.domains.resource.Resource;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.resource.ResourceDto;

import java.util.List;

public interface ResourceService {
    List<Resource> findAll();

    Resource findById(String resourceId);

    Resource findByName(String resourceName);

    Resource addResource(ResourceCreationDto resourceCreationDto);

    Resource updateResource(ResourceDto resourceDto);

    void deleteById(String resourceId);

    void deleteByName(String resourceName);
}
