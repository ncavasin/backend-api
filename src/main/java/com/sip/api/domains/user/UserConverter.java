package com.sip.api.domains.user;

import com.sip.api.domains.role.Role;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.dtos.user.UserSlimDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserConverter {

    public static User dtoToEntity(UserCreationDto userCreationDto) {
        return new User(
                userCreationDto.getDni(),
                userCreationDto.getPassword(),
                userCreationDto.getEmail(),
                userCreationDto.getFirstName(),
                userCreationDto.getLastName(),
                userCreationDto.getBirthDate(),
                userCreationDto.getPhone());
    }

    public static User slimDtoToEntity(UserSlimDto userSlimDto) {
        return new User(userSlimDto.getId(), userSlimDto.getEmail(), userSlimDto.getFirstName(), userSlimDto.getLastName());
    }

    public static UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .dni(user.getDni())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .phone(user.getPhone())
                .status(user.getStatus())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
    }

    public static UserSlimDto entityToDtoSlim(User user) {
        return UserSlimDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public static List<UserDto> entityToDto(List<User> users) {
        return users.stream().map(UserConverter::entityToDto).collect(Collectors.toList());
    }
}
