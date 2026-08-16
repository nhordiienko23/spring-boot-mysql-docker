package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface UserService {
    public ResponseEntity<User> getUserById(long id);
    public List<User> searchUsers(String firstName, String lastName);
    public ResponseEntity<User> searchUsersByEmail(String email);
    public List<User> searchUsersByFirstName(String firstName);
    List<User> searchUsersByLastName(String lastName);
    List<User> showAllUsers();
}
