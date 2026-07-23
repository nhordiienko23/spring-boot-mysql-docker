package com.github.nhordiienko23.springmysql.repository;

import com.github.nhordiienko23.springmysql.model.User;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    List<User> findByLastNameIgnoreCase(String lastName);
    List<User> findByFirstNameIgnoreCase(String firstName);
    Optional<User> findByEmail(String email);
}
