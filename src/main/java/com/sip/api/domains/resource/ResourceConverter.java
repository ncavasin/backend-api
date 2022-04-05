package com.sip.api.domains.resource;

import com.sip.api.dtos.resource.ResourceDto;

import java.util.List;
import java.util.stream.Collectors;

public class ResourceConverter {
    public static ResourceDto entityToDto(Resource resource) {
        return ResourceDto.builder()
                .id(resource.getId())
                .name(resource.getName())
                .build();
    }

    public static List<ResourceDto> entityToDto(List<Resource> resources) {
        return resources.stream()
                .map(ResourceConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public static Resource dtoToEntity(ResourceDto resourceDto) {
        return Resource.builder()
                .name(resourceDto.getName())
                .build();
    }
}
