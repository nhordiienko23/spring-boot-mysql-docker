package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.dto.UserDto;
import org.springframework.ui.Model;

public interface RegisterService {
    String register(String username,
                     String email,
                     String password,
                     Model model);
}
