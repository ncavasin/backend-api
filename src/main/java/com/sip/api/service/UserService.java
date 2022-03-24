package com.sip.api.service;

import com.sip.api.controller.UserPasswordDto;
import com.sip.api.domain.user.User;
import com.sip.api.dto.user.UserDto;
import com.sip.api.dto.user.UserEmailDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();

    User findById(String userId);

    User createUser(UserDto userCreationDto);

    User updateEmail(String userId, UserEmailDto userEmailDto);

    User updatePassword(String userId, UserPasswordDto userPasswordDto);

    User activateUser(String userId);

    void deactivateUser(String userId);

    void deleteUser(String userId);
}
