package com.github.nhordiienko23.springmysql.repository;

import com.github.nhordiienko23.springmysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
}
