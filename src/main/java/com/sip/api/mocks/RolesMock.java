//package com.sip.api.mocks;
//
//import com.sip.api.domains.role.Role;
//import com.sip.api.dtos.RoleCreationDto;
//import com.sip.api.services.ResourceService;
//import com.sip.api.services.RoleService;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.List;
//
//@Data
//@Component
///**
// * Creates and persists roles in the database.
// */
//public class RolesMock {
//    private Role adminRole;
//    private Role userRole;
//    private Role professorRole;
//    private Role analystRole;
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private ResourceService resourceService;
//
//
//    @Autowired
//    public RolesMock() {
//        this.adminRole = mockAdminRole();
//        this.userRole = mockUserRole();
//        this.professorRole = mockProfessorRole();
//        this.analystRole = mockAnalystRole();
//    }
//
//    private Role mockRole(String name, List<String> allowedResourcesIds) {
//        return roleService.createRole(RoleCreationDto.builder()
//                .name(name)
//                .allowedResourcesIds(allowedResourcesIds)
//                .build());
//    }
//
//    private Role mockProfessorRole() {
//        return mockRole("PROFESSOR_ROLE", List.of(resourceService.findByName("MANAGEMENT").getId(),
//                resourceService.findByName("USER").getId()));
//    }
//
//    private Role mockUserRole() {
//        return mockRole("USER_ROLE", List.of(resourceService.findByName("USER").getId(),
//                resourceService.findByName("LOGIN").getId(),
//                resourceService.findByName("LOGOUT").getId()));
//    }
//
//    private Role mockAnalystRole() {
//        return mockRole("ANALYST_ROLE", List.of(resourceService.findByName("MANAGEMENT").getId(),
//                resourceService.findByName("USER").getId()));
//    }
//
//    private Role mockAdminRole() {
//        return mockRole("ADMIN_ROLE", Collections.singletonList(resourceService.findByName("ALL").getId()));
//    }
//}
