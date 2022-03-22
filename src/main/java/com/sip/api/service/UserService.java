package com.sip.api.service;

import com.sip.api.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findAll();

    User findById(String userId);

    User createUser(UserCreationDto userCreationDto);

    User updatUsername(String userId, UserUsernameDto userUsernameDto);

    User updateEmail(String userId, UserEmailDto userEmailDto);

    User updatePassword(String userId, UserPasswordDto userPasswordDto);

    User activateUser(String userId);

    void deactivateUser(String userId);

    void deleteUser(String userId);
}
