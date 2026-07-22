package com.github.nhordiienko23.springmysql.repository;

import com.github.nhordiienko23.springmysql.model.User;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    List<User> findByLastNameIgnoreCase(String lastName);
    List<User> findByFirstNameIgnoreCase(String firstName);
    List<User> findByEmail(String email);
}
