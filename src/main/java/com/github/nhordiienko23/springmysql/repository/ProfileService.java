package com.github.nhordiienko23.springmysql.repository;

import com.github.nhordiienko23.springmysql.dto.UserDto;



public interface ProfileService {

    UserDto getProfile(Long id);
    UserDto updateProfile(Long id, UserDto userDto);
    void deleteProfile(Long id);

}
