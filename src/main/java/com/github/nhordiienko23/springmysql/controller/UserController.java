package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "returns a user by id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        return userRepository.findById(id).
                map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @Operation(summary = "returns a list of users by firstName and lastName case sensitive")
    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        return userRepository.findByFirstNameAndLastName (firstName, lastName);
    }
    @Operation(summary = "returns a list of users by email")
    @GetMapping("/by-email")
    public ResponseEntity<User> searchUsersByEmail(@RequestParam String email){
        return userRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @Operation(summary = "returns a list of users by firstName IgnoreCase")
    @GetMapping("/by-firstName")
    public List<User> searchUsersByFirstName(@RequestParam String firstName){
        return userRepository.findByFirstNameIgnoreCase(firstName);
    }
    @Operation(summary = "returns a list of users by lastName IgnoreCase")
    @GetMapping("/by-lastName")
    public List<User> searchUsersByLastName(
            @RequestParam String lastName
    ){
        return userRepository.findByLastNameIgnoreCase(lastName);
    }

    @Operation(summary = "returns a list of all users")
    @GetMapping
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

}
