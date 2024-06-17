package com.person.service;

import com.person.model.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    Users getUser(Long userId);
}
