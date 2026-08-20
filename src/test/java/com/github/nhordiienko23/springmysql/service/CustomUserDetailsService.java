package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.Role;
import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    void shouldLoadUserByUsernameSuccessfully() {

        User mockUser = User.builder()
                .firstName("Nikita")
                .password("encoded_pass")
                .roles(List.of(Role.ROLE_ADMIN))
                .build();

        when(userRepository.findByFirstName("Nikita")).thenReturn(Optional.of(mockUser));


        UserDetails userDetails = userDetailsService.loadUserByUsername("Nikita");


        assertNotNull(userDetails);
        assertEquals("Nikita", userDetails.getUsername());
        assertEquals("encoded_pass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findByFirstName("Unknown")).thenReturn(Optional.empty());


        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("Unknown");
        });
    }
}