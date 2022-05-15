package com.sip.api.services;

import com.sip.api.domains.availableClass.AvailableClass;
import com.sip.api.dtos.availableClass.AvailableClassDto;
import com.sip.api.dtos.availableClass.AvailableClassesCreationDto;

import java.util.List;

public interface AvailableClassService {
    List<AvailableClass> findAll();

    AvailableClass findById(String appointmentId);

    AvailableClass createAvailableClass(AvailableClassesCreationDto availableClassesCreationDto);

    void removeAvailableClass(String appointmentId);

    AvailableClass updateAvailableClass(AvailableClassDto availableClassDto);
}
