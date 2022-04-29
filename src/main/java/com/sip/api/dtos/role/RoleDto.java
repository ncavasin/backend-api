package com.sip.api.dtos.role;

import com.sip.api.domains.resource.Resource;
import lombok.Builder;

import java.util.Set;

@Builder
public record RoleDto(String id, String name, Set<Resource> allowedResources) {
}
