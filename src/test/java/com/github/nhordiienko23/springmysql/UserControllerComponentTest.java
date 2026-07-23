package com.github.nhordiienko23.springmysql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerComponentTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /users/{id} - must return user by ID")
    void shouldReturnUserById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /users/{id} - must return 404 code")
    void shouldReturn404Code()throws Exception{
        mockMvc.perform(get("/users/421"))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("GET /users/by-lastName?lastName=Hordiienko - must return list of users with the same lastName")
    void shouldReturnListUsersByLastName()throws Exception{
        mockMvc.perform(get("/users/by-lastName").param("lastName","Hordiienko"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$..lastName",everyItem(equalToIgnoringCase("Hordiienko"))));
    }

    @Test
    @DisplayName("GET /users/by-lastName?lastName=asdasd - must return empty list")
    void shouldReturnEmptyListByLastName()throws Exception{
        mockMvc.perform(get("/users/by-lastName").param("lastName","asdasd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(0)));
    }

  @Test
   @DisplayName(("GET/users - must return all users"))
   void shouldReturnAllUsers()throws Exception {
      mockMvc.perform(get("/users"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$").isArray());
  }


      @Test
      @DisplayName("GET/users/by-email?email=nhordiienko23@gmail.com - must return user by email")
        void shouldReturnUserByEmail()throws Exception{
            mockMvc.perform(get("/users/by-email").param("email","nhordiienko23@gmail.com"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value("nhordiienko23@gmail.com"));
      }
    @Test
    @DisplayName("GET/users/by-email?email=nhordiienko21233@gmail.com - must return 404Code by email")
    void shouldReturn404CodeByEmail()throws Exception{
        mockMvc.perform(get("/users/by-email").param("email","nhordiienko21233@gmail.com"))
                .andExpect(status().isNotFound());
    }

      @Test
        @DisplayName("GET/users/by-firstName?firstName=Nikita - must return all users with the same firstName")
      void shouldReturnUserByFirstName()throws Exception{
            mockMvc.perform(get("/users/by-firstName").param("firstName","Nikita"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$..firstName",everyItem(equalToIgnoringCase("Nikita"))));

      }
    @Test
    @DisplayName("GET/users/by-firstName?firstName=asdasd - must return empty list")
    void shouldReturnEmptyListByFirstName()throws Exception{
        mockMvc.perform(get("/users/by-firstName").param("firstName","asdasd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(0)));

    }



      @Test
      @DisplayName("GET/users/search?firstName=Nikita&lastName=Hordiienko - must return all users with the same firstName and lastName")
      void shouldReturnUserListByFirstNameAndLastName()throws Exception{
            mockMvc.perform(get("/users/search")
                            .param("firstName","Nikita")
                            .param("lastName","Hordiienko")
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$..firstName",everyItem(is("Nikita"))))
                    .andExpect(jsonPath("$..lastName",everyItem(is("Hordiienko"))));

        }
    @Test
    @DisplayName("GET/users/search?firstName=asd&lastName=asd - must return empty list")
    void shouldReturnEmptyLiseByFirstNameAndLastName()throws Exception{
        mockMvc.perform(get("/users/search")
                        .param("firstName","asd")
                        .param("lastName","asd")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(0)));

    }

}
