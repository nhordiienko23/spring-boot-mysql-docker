package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<User> getUserById(long id) {
        return userRepository.findById(id).
                map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @Override
    public List<User> searchUsers(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName (firstName, lastName);
    }

    @Override
    public ResponseEntity<User> searchUsersByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @Override
    public List<User> searchUsersByFirstName(String firstName) {
        return userRepository.findByFirstNameIgnoreCase(firstName);
    }

    @Override
    public List<User> searchUsersByLastName(String lastName) {
        return userRepository.findByLastNameIgnoreCase(lastName);
    }

    @Override
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }
}
