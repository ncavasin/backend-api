package com.sip.api.domains.user;

import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDto;
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
                userCreationDto.getAge(),
                userCreationDto.getPhone(),
                userCreationDto.getZipCode());
    }


    public static UserDto entityToDto(User user) {
        return UserDto.builder()
                .dni(user.getDni())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .phone(user.getPhone())
                .zipCode(user.getZipCode())
                .status(user.getStatus())
                .build();
    }

    public static List<UserDto> entityToDto(List<User> users) {
        return users.stream().map(UserConverter::entityToDto).collect(Collectors.toList());
    }

}
