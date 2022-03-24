package com.sip.api.controllers;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserPasswordDto {
    @NonNull
    @Size(min = 8, max = 255)
    private String password;
}