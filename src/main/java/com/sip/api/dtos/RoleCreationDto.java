package com.sip.api.dtos;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
public record RoleCreationDto(String name, @NotEmpty List<String> allowedResourcesIds) {
}
