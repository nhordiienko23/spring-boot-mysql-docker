package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<User> getUserById(long id) {
        return userRepository.findById(id).
                map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<User> searchUsers(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName (firstName, lastName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ResponseEntity<User> searchUsersByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<User> searchUsersByFirstName(String firstName) {
        return userRepository.findByFirstNameIgnoreCase(firstName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<User> searchUsersByLastName(String lastName) {
        return userRepository.findByLastNameIgnoreCase(lastName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }
}       
