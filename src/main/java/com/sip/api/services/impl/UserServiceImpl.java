package com.sip.api.services.impl;

import com.sip.api.controllers.UserPasswordDto;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserDto;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(String userId) {
        return null;
    }

    @Override
    public User createUser(UserDto userCreationDto) {
        return null;
    }

    @Override
    public User updateEmail(String userId, UserEmailDto userEmailDto) {
        return null;
    }

    @Override
    public User updatePassword(String userId, UserPasswordDto userPasswordDto) {
        return null;
    }

    @Override
    public User activateUser(String userId) {
        return null;
    }

    @Override
    public void deactivateUser(String userId) {

    }

    @Override
    public void deleteUser(String userId) {

    }
}
