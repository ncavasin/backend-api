package com.sip.api.services;

import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDniDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.dtos.user.UserPasswordDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findById(String userId);

    User createUser(UserCreationDto userCreationDto);

    User updateEmail(String userId, UserEmailDto userEmailDto);

    User updatePassword(String userId, UserPasswordDto userPasswordDto);

    User activateUser(String userId);

    void deactivateUser(String userId);

    void deleteUser(String userId);

    User findByDni(UserDniDto userDniDto);

    User findByEmail(UserEmailDto userEmailDto);

    boolean existsByEmail(String email);
}
