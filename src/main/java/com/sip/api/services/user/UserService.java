package com.sip.api.services.user;

import com.sip.api.controllers.UserPasswordDto;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.dtos.user.UserEmailDto;
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
