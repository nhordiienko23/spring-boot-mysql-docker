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
    public String register(String username,
                           String email,
                           String password,
                           Model model) {
        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        User newUser = User.builder().
                firstName(username).
                email(email).
                password(passwordEncoder.encode(password)).
                registeredAt(LocalDateTime.now())
                .roles(List.of(Role.ROLE_USER))
                .build();
        userRepository.save(newUser);
        model.addAttribute("success", "Registration successful! You can now log in.");
         return "redirect:/login";
    }
}
