package com.sip.api.domains.user;

import com.sip.api.domains.role.Role;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public record UserConverter() {

    public static User dtoToEntity(UserCreationDto userCreationDto) {
        return new User(
                userCreationDto.dni(),
                userCreationDto.password(),
                userCreationDto.email(),
                userCreationDto.firstName(),
                userCreationDto.lastName(),
                userCreationDto.age(),
                userCreationDto.phone());
    }


    public static UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .dni(user.getDni())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .phone(user.getPhone())
                .status(user.getStatus())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
    }

    public static List<UserDto> entityToDto(List<User> users) {
        return users.stream().map(UserConverter::entityToDto).collect(Collectors.toList());
    }

}
