package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        return userRepository.findById(id).
                map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
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
