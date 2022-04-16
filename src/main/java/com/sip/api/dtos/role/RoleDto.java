package com.sip.api.dtos.role;

import com.sip.api.domains.resource.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private String id;

    private String name;

    private Set<Resource> allowedResources;
}
