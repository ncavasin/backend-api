package com.sip.api.dtos.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {
    @NotEmpty
    private String id;
    @NotEmpty
    private String name;
    private String url;
}
