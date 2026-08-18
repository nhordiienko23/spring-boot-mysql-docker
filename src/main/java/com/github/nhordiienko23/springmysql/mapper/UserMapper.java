package com.github.nhordiienko23.springmysql.mapper;

import com.github.nhordiienko23.springmysql.dto.UserDto;
import com.github.nhordiienko23.springmysql.model.User;

public interface UserMapper {
    UserDto toUserDto(User user);
}
