package com.github.nhordiienko23.springmysql.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Test
    void publicEndpointsShouldBeAccessibleWithoutAuth() throws Exception {

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
    }

    @Test
    void protectedEndpointsShouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is3xxRedirection());
    }
}