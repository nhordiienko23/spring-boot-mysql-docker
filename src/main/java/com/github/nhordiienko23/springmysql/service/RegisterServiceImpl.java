package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.dto.UserDto;
import com.github.nhordiienko23.springmysql.model.Role;
import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(String username,
                           String email,
                           String password
                           ) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        User newUser = User.builder().
                firstName(username).
                email(email).
                password(passwordEncoder.encode(password)).
                registeredAt(LocalDateTime.now())
                .roles(List.of(Role.ROLE_USER))
                .build();
        userRepository.save(newUser);
    }
}
