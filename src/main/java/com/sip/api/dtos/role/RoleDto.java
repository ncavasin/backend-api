package com.sip.api.dtos.role;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private String id;
    @NonNull
    private String name;

}
