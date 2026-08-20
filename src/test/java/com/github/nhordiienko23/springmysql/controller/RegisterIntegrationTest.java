package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.model.Role;
import com.github.nhordiienko23.springmysql.model.User;
import com.github.nhordiienko23.springmysql.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegisterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private JsonMapper.Builder builder;

    @Test
    void shouldRegisterNewUserSuccessfully() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "h2_user")
                        .param("email", "h2@example.com")
                        .param("password", "supersecret123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        assertTrue(userRepository.findByEmail("h2@example.com").isPresent());
    }

    @Test
    void shouldReturnExceptionEmailAlreadyExists()throws Exception{
        User user = User.builder()
                .firstName("nikita")
                .password("qwe")
                .email("nikita@n")
                .roles(List.of(Role.ROLE_USER))
                .registeredAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        mockMvc.perform(post("/register")
                .param("username","nikita")
                .param("email","nikita@n")
                .param("password","qwe"))

                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Email is already in use!"));

    }
}