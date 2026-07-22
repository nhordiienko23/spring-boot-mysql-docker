package com.github.nhordiienko23.springmysql.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/by-lastName")
    public List<User> searchUsersByLastName(
            @RequestParam String lastName
    ){
        return userRepository.findByLastNameIgnoreCase(lastName);
    }

    @GetMapping
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

}
