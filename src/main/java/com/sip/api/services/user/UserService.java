package com.sip.api.services.user;

import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.dtos.user.UserPasswordDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();

    User findById(String userId);

    User createUser(UserCreationDto userCreationDto);

    User updateEmail(String userId, UserEmailDto userEmailDto);

    User updatePassword(String userId, UserPasswordDto userPasswordDto);

    User activateUser(String userId);

    void deactivateUser(String userId);

    void deleteUser(String userId);

    User findByEmail(UserEmailDto userEmailDto);
}
