package com.github.nhordiienko23.springmysql.dto;

import lombok.Builder;

@Builder
public record UserDto(String username,String email) {
}
