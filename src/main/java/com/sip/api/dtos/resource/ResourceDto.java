package com.sip.api.dtos.resource;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;

@Builder
public record ResourceDto(@NotEmpty String id, @NotEmpty String name, String url) {
}
