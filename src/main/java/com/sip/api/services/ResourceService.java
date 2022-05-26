package com.sip.api.services;

import com.sip.api.domains.resource.Resource;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.resource.ResourceDto;
import com.sip.api.dtos.resource.ResourceNameDto;
import com.sip.api.dtos.resource.ResourceUrlDto;

import java.util.List;

public interface ResourceService {
    List<Resource> findAll();

    Resource findById(String resourceId);

    Resource findByName(String resourceName);

    Resource findByUrl(String resourceUrl);

    Resource createResource(ResourceCreationDto resourceCreationDto);

    Resource updateResourceName(ResourceNameDto resourceNameDto);

    Resource updateResourceUrl(ResourceUrlDto resourceUrlDto);

    void deleteById(String resourceId);

    void deleteByName(String resourceName);

    void deleteByUrl(String resourceUrl);

    boolean existsById(String resourceId);

    boolean existsByName(String resourceName);

    boolean existsByUrl(String resourceUrl);
}
