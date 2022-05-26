package com.sip.api.controllers;

import com.sip.api.domains.resource.ResourceConverter;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.resource.ResourceDto;
import com.sip.api.dtos.resource.ResourceNameDto;
import com.sip.api.dtos.resource.ResourceUrlDto;
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

    @PostMapping("/find-by-name")
    public ResourceDto getResourceByName(@RequestBody @Valid ResourceDto resourceDto) {
        return ResourceConverter.entityToDto(resourceService.findByName(resourceDto.getName()));
    }

    @GetMapping("/all")
    public List<ResourceDto> getAll() {
        return ResourceConverter.entityToDto(resourceService.findAll());
    }

    @PostMapping
    public ResourceDto createResource(@RequestBody @Valid ResourceCreationDto resourceCreationDto) {
        return ResourceConverter.entityToDto(resourceService.createResource(resourceCreationDto));
    }

    @PutMapping("/update-name")
    public ResourceDto updateResourceName(@RequestBody @Valid ResourceNameDto resourceNameDto) {
        return ResourceConverter.entityToDto(resourceService.updateResourceName(resourceNameDto));
    }

    @PutMapping("/update-url")
    public ResourceDto updateResourceUrl(@RequestBody @Valid ResourceUrlDto resourceUrlDto) {
        return ResourceConverter.entityToDto(resourceService.updateResourceUrl(resourceUrlDto));
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
