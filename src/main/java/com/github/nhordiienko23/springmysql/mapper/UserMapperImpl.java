package com.github.nhordiienko23.springmysql.mapper;

import com.github.nhordiienko23.springmysql.dto.UserDto;
import com.github.nhordiienko23.springmysql.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .username(user.getFirstName())
                .email(user.getEmail()).build();
    }
}
