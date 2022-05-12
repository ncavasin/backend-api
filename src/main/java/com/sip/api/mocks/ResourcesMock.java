//package com.sip.api.mocks;
//
//import com.sip.api.domains.resource.Resource;
//import com.sip.api.dtos.resource.ResourceCreationDto;
//import com.sip.api.services.ResourceService;
//import lombok.Data;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Data
//@Component
///**
// * Creates and persists resources in the database.
// */
//public class ResourcesMock {
//    private Resource allResource;
//    private Resource userResource;
//    private Resource managementResource;
//    private Resource resourceResource;
//    private Resource roleResource;
//    private Resource loginResource;
//    private Resource logoutResource;
//
//    private final ResourceService resourceService;
//
//    public ResourcesMock(ResourceService resourceService) {
//        this.resourceService = resourceService;
//        this.allResource = mockAllResource();
//        this.userResource = mockUserResource();
//        this.managementResource = mockManagementResource();
//        this.resourceResource = mockResourceResource();
//        this.roleResource = mockRoleResource();
//        this.loginResource = mockLoginResource();
//        this.logoutResource = mockLogoutResource();
//    }
//
//    private Resource mockLogoutResource() {
//        return mockResource("LOGOUT", "/logout");
//    }
//
//    private Resource mockLoginResource() {
//        return mockResource("LOGIN", "/login");
//    }
//
//    public List<Resource> getAllMockedResources() {
//        return List.of(allResource, managementResource, userResource, resourceResource, roleResource);
//    }
//
//    private Resource mockRoleResource() {
//        return mockResource("ROLES", "/management/role");
//    }
//
//    private Resource mockResourceResource() {
//        return mockResource("RESOURCES", "/management/resource");
//    }
//
//    private Resource mockManagementResource() {
//        return mockResource("MANAGEMENT", "/management");
//    }
//
//
//    private Resource mockUserResource() {
//        return mockResource("USERS", "/user");
//    }
//
//    private Resource mockAllResource() {
//        return mockResource("ALL", "/*");
//    }
//
//    private Resource mockResource(String name, String url) {
//        if (resourceService.existsByName(name)) return resourceService.findByName(name);
//        return resourceService.createResource(ResourceCreationDto.builder()
//                .name(name)
//                .url(url)
//                .build());
//    }
//
//}
