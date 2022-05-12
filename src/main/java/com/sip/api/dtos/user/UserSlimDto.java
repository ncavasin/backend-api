package com.sip.api.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSlimDto {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}
