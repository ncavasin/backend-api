package com.sip.api.controllers;

import com.sip.api.domains.availableClass.AvailableClassConverter;
import com.sip.api.dtos.availableClass.AvailableClassDto;
import com.sip.api.dtos.availableClass.AvailableClassesCreationDto;
import com.sip.api.services.AvailableClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/available-class")
@RequiredArgsConstructor
public class AvailableClassController {
    private final AvailableClassService availableClassService;

    @GetMapping("/all")
    public List<AvailableClassDto> findAll() {
        return AvailableClassConverter.fromEntityToDto(availableClassService.findAll());
    }

    @GetMapping("/{availableClassId}")
    public AvailableClassDto findById(@PathVariable("availableClassId") String availableClassId) {
        return AvailableClassConverter.fromEntityToDto(availableClassService.findById(availableClassId));
    }

    @PostMapping
    public AvailableClassDto createAvailableClass(@RequestBody @Valid AvailableClassesCreationDto availableClassesCreationDto) {
        return AvailableClassConverter.fromEntityToDto(availableClassService.createAvailableClass(availableClassesCreationDto));
    }

    @PutMapping("/{availableClassId}/add-user/{userId}")
    public AvailableClassDto addUserToAvailableClass(@PathVariable("availableClassId") String availableClassId, @PathVariable("userId") String userId) {
        return AvailableClassConverter.fromEntityToDto(availableClassService.addUserToAvailableClass(availableClassId, userId));
    }

    @PutMapping("/{availableClassId}/remove-user/{userId}")
    public AvailableClassDto removeUserFromAvailableClass(@PathVariable("availableClassId") String availableClassId, @PathVariable("userId") String userId) {
        return AvailableClassConverter.fromEntityToDto(availableClassService.removeUserFromAvailableClass(availableClassId, userId));
    }

    @DeleteMapping("/{availableClassId}")
    public void removeAvailableClassById(@PathVariable("availableClassId") String availableClassId) {
        availableClassService.removeAvailableClass(availableClassId);
    }

}
