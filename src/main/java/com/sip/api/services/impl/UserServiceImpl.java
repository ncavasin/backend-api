package com.sip.api.services.impl;

import com.sip.api.domains.role.Role;
import com.sip.api.domains.enums.UserStatus;
import com.sip.api.domains.user.UserConverter;
import com.sip.api.dtos.user.UserCreationDto;
import com.sip.api.dtos.user.UserDniDto;
import com.sip.api.dtos.user.UserPasswordDto;
import com.sip.api.domains.user.User;
import com.sip.api.dtos.user.UserEmailDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
import com.sip.api.repositories.UserRepository;
import com.sip.api.services.RoleService;
import com.sip.api.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User findByDni(UserDniDto userDniDto) {
        return userRepository.findByDni(userDniDto.getDni()).orElseThrow(() -> new NotFoundException("DNI not found"));
    }

    @Override
    public User findByEmail(UserEmailDto userEmailDto) {
        return userRepository.findByEmail(userEmailDto.getEmail()).orElseThrow(() -> new NotFoundException("Email not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        User user = findByEmail(new UserEmailDto(email));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public User createUser(UserCreationDto userCreationDto) {
        checkUserRules(userCreationDto.getDni(), userCreationDto.getEmail(), userCreationDto.getPassword(), userCreationDto.getRolesNames());
        User user = UserConverter.dtoToEntity(userCreationDto);
        // Fetch roles by name
        Set<Role> roles = validateRoles(userCreationDto.getRolesNames());
        user.setRoles(roles);
        // Created inactive by default until user activates it by mail
        user.setStatus(UserStatus.INACTIVE);
        user.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
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
        User user = findById(userId);
        user.setPassword(passwordEncoder.encode(userPasswordDto.getPassword()));
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

    private void checkUserRules(Integer dni, String email, String password, List<String > rolesIds) {
        validateDni(dni);
        validateEmail(email);
        validatePassword(password);
        validateRoles(rolesIds);
    }

    private Set<Role> validateRoles(List<String> roles) {
        if(roles == null || roles.isEmpty()) throw new BadRequestException("Roles are required");
        return roles.stream().map(roleService::findByName).collect(Collectors.toSet());
    }

    private Integer validateDni(Integer dni) {
        if ((dni != 0) && userRepository.existsByDni(dni))
            throw new BadRequestException("DNI already in use");
        return dni;
    }

    private String validatePassword(String password) {
        if (password.length() < 8) throw new BadRequestException("Password smaller than 8");
        return password;
    }

    private String validateEmail(String email) {
        if ((!email.isEmpty()) && userRepository.existsByEmail(email))
            throw new BadRequestException("Email already in use");
        return email;
    }
}
