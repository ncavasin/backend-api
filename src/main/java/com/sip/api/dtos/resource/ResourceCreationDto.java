package com.sip.api.dtos.resource;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;

@Builder
public record ResourceCreationDto(@NotEmpty String name, @NotEmpty String url) {
}
