package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "returns a user by id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "returns a list of users by firstName and lastName case sensitive")
    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        return userService.searchUsers(firstName, lastName);
    }

    @Operation(summary = "returns a list of users by email")
    @GetMapping("/by-email")
    public ResponseEntity<User> searchUsersByEmail(@RequestParam String email) {
        return userService.searchUsersByEmail(email);
    }

    @Operation(summary = "returns a list of users by firstName IgnoreCase")
    @GetMapping("/by-firstName")
    public List<User> searchUsersByFirstName(@RequestParam String firstName) {
        return userService.searchUsersByFirstName(firstName);
    }

    @Operation(summary = "returns a list of users by lastName IgnoreCase")
    @GetMapping("/by-lastName")
    public List<User> searchUsersByLastName(
            @RequestParam String lastName
    ) {
        return userService.searchUsersByLastName(lastName);
    }

    @Operation(summary = "returns a list of all users")
    @GetMapping
    public List<User> showAllUsers() {
        return userService.showAllUsers();
    }
}
