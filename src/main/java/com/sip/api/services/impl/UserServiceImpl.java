package com.sip.api.services.impl;

import com.sip.api.domains.enums.UserStatus;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserPasswordDto;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.UserRepository;
import com.sip.api.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User findByEmail(UserEmailDto userEmailDto) {
        return userRepository.findByEmail(userEmailDto.getEmail()).orElseThrow(() -> new NotFoundException("Email not found"));
    }

    @Override
    public User createUser(UserCreationDto userCreationDto) {
        checkUserRules(userCreationDto.getDni(), userCreationDto.getEmail(), userCreationDto.getPassword());
        User user = UserConverter.dtoToEntity(userCreationDto);
        // Created inactive by default until user pays the subscription plan
        user.setStatus(UserStatus.INACTIVE);
        // TODO encrypt pwd
        return userRepository.save(user);
    }

    @Override
    public User updateEmail(String userId, UserEmailDto userEmailDto) {
        validateEmail(userEmailDto.getEmail());
        User user = findById(userId);
        user.setEmail(userEmailDto.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(String userId, UserPasswordDto userPasswordDto) {
        validatePassword(userPasswordDto.getPassword());
        // TODO encrypt pwd
        User user = findById(userId);
        user.setPassword(userPasswordDto.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User activateUser(String userId) {
        User user = findById(userId);
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public void deactivateUser(String userId) {
        User user = findById(userId);
        user.setStatus(UserStatus.DEACTIVATED);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }

    private void checkUserRules(Integer dni, String email, String password) {
        validateDni(dni);
        validateEmail(email);
        validatePassword(password);
    }

    private Integer validateDni(Integer dni) {
        if ((dni != 0) && userRepository.existsByDni(dni))
            throw new BadRequestException("DNI already in use");
        return dni;
    }

    private String validatePassword(String password) {
        if (password.length() < 8) throw new BadRequestException("Password smaller than 8");
        // TODO encrypt pwd
        return password;
    }

    private String validateEmail(String email) {
        if ((!email.isEmpty()) && userRepository.existsByEmail(email))
            throw new BadRequestException("Email already in use");
        return email;
    }
}
