package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.dto.UserDto;
import com.github.nhordiienko23.springmysql.mapper.UserMapper;
import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.ProfileService;
import com.github.nhordiienko23.springmysql.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ProfileServiceImpl implements ProfileService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ProfileServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto getProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateProfile(Long id, UserDto userDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setFirstName(userDto.username());
        user.setEmail(userDto.email());
        user.setLastUpdateAt(LocalDateTime.now());
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public void deleteProfile(Long id) {
        userRepository.deleteById(id);
    }


}
