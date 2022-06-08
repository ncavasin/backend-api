package com.sip.api.utils.mock;

import com.sip.api.domains.resource.Resource;
import com.sip.api.domains.role.Role;

import java.util.Set;

public class RoleMocking {
    public static Role generateRawRoleWithParams(String name, Set<Resource> allowedResources) {
        return Role.builder()
                .name(name)
                .allowedResources(allowedResources)
                .build();
    }
}
