package com.github.nhordiienko23.springmysql.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nhordiienko23.springmysql.dto.UserDto;
import com.github.nhordiienko23.springmysql.model.Role;
import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import com.github.nhordiienko23.springmysql.service.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ProfileIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ClientRegistrationRepository clientRegistrationRepository;

    private User testUser;
    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUser = User.builder()
                .firstName("integrationTest")
                .email("test@spring.com")
                .password("encoded_password")
                .registeredAt(LocalDateTime.now())
                .roles(List.of(Role.ROLE_USER))
                .build();

        testUser = userRepository.save(testUser);

        customUserDetails = new CustomUserDetails(testUser);
    }

    @Test
    @DisplayName("Should return UserDto when successfully retrieving profile")
    void getProfile_ShouldReturnUserDto() throws Exception {
        mockMvc.perform(get("/profile")
                        .with(user(customUserDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@spring.com"))
                .andExpect(jsonPath("$.username").value("integrationTest"));
    }

    @Test
    @DisplayName("Should redirect to login when unauthenticated user accesses profile")
    void getProfile_WhenUnauthenticated_ShouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Should update profile and return updated UserDto")
    void updateProfile_ShouldUpdateAndReturnUserDto() throws Exception {
        UserDto updateRequest = new UserDto("newName", "newemail@spring.com");

        mockMvc.perform(put("/profile")
                        .with(user(customUserDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newemail@spring.com"))
                .andExpect(jsonPath("$.username").value("newName"));

        User updatedUserInDb = userRepository.findById(testUser.getId()).orElseThrow();
        assertEquals("newemail@spring.com", updatedUserInDb.getEmail());
        assertEquals("newName", updatedUserInDb.getFirstName());
    }

    @Test
    @DisplayName("Should return 400 Bad Request when updating with invalid email")
    void updateProfile_WithInvalidEmail_ShouldReturn400BadRequest() throws Exception {
        UserDto invalidRequest = new UserDto("newName", "invalid-email-format");

        mockMvc.perform(put("/profile")
                        .with(user(customUserDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should successfully delete profile and logout user")
    void deleteProfile_ShouldDeleteUserAndReturn200() throws Exception {
        mockMvc.perform(delete("/profile")
                        .with(user(customUserDetails)))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));

        assertTrue(userRepository.findById(testUser.getId()).isEmpty());
    }
}