package com.sip.api.controllers;

import com.sip.api.domains.resource.ResourceConverter;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.resource.ResourceDto;
import com.sip.api.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/management/resource")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;

    @GetMapping("/{resourceId}")
    public ResourceDto getResourceById(@PathVariable("resourceId") String resourceId) {
        return ResourceConverter.entityToDto(resourceService.findById(resourceId));
    }

    @GetMapping
    public ResourceDto getResourceByName(@RequestBody @Valid ResourceDto resourceDto) {
        return ResourceConverter.entityToDto(resourceService.findByName(resourceDto.getName()));
    }

    @GetMapping("/all")
    public List<ResourceDto> getAll() {
        return ResourceConverter.entityToDto(resourceService.findAll());
    }

    @PostMapping
    public ResourceDto createResource(@RequestBody @Valid ResourceCreationDto resourceCreationDto) {
        return ResourceConverter.entityToDto(resourceService.addResource(resourceCreationDto));
    }

    @PutMapping
    public ResourceDto updateResource(@RequestBody @Valid ResourceDto resourceDto) {
        return ResourceConverter.entityToDto(resourceService.updateResource(resourceDto));
    }

    @DeleteMapping("/{resourceId}")
    public void deleteById(@PathVariable("resourceId") String resourceId) {
        resourceService.deleteById(resourceId);
    }

    @DeleteMapping
    public void deleteByName(@RequestBody @Valid ResourceDto resourceDto) {
        resourceService.deleteByName(resourceDto.getName());
    }
}
