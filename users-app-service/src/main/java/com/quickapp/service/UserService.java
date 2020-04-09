package com.quickapp.service;

import com.quickapp.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);

    UserDto getUserByEmail(String email);

    UserDto getUserByUserId(String userId);

    UserDto getUserByToken(String token);
}