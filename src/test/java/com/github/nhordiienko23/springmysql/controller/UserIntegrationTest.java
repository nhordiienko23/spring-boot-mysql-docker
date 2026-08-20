package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.model.Role;
import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user1 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .roles(List.of(Role.ROLE_USER))
                .registeredAt(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("password123")
                .roles(List.of(Role.ROLE_USER))
                .registeredAt(LocalDateTime.now())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }



    @Test
    void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetUserByIdSuccessfully() throws Exception {
        User savedUser = userRepository.findAll().get(0);

        mockMvc.perform(get("/users/" + savedUser.getId())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(savedUser.getFirstName())))
                .andExpect(jsonPath("$.email", is(savedUser.getEmail())));
    }

    @Test
    void shouldSearchUsersByFirstNameAndLastName() throws Exception {
        mockMvc.perform(get("/users/search")
                        .with(user("admin").roles("ADMIN"))
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    void shouldSearchUserByEmailSuccessfully() throws Exception {
        mockMvc.perform(get("/users/by-email")
                        .with(user("admin").roles("ADMIN"))
                        .param("email", "john.doe@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));
    }

    @Test
    void shouldSearchUsersByFirstNameIgnoreCase() throws Exception {
        mockMvc.perform(get("/users/by-firstName")
                        .with(user("admin").roles("ADMIN"))
                        .param("firstName", "jOhN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    void shouldSearchUsersByLastNameIgnoreCase() throws Exception {
        mockMvc.perform(get("/users/by-lastName")
                        .with(user("admin").roles("ADMIN"))
                        .param("lastName", "smith")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastName", is("Smith")));
    }



    @Test
    void shouldReturnNotFoundWhenUserByIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/users/99999") // Несуществующий ID
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenEmailDoesNotExist() throws Exception {
        mockMvc.perform(get("/users/by-email")
                        .with(user("admin").roles("ADMIN"))
                        .param("email", "nobody@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnEmptyListWhenFirstNameNotFound() throws Exception {
        mockMvc.perform(get("/users/by-firstName")
                        .with(user("admin").roles("ADMIN"))
                        .param("firstName", "Unknown")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldForbiddenForRegularUserAccessingUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .with(user("regular_guy").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Ожидаем 403
    }
}