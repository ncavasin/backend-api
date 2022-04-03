package com.sip.api.dtos.role;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private String id;

    private String name;
}
