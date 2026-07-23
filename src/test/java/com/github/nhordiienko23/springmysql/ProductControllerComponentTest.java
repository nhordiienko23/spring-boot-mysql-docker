package com.github.nhordiienko23.springmysql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ProductControllerComponentTest extends AbstractIntegrationTest{
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("GET/products - must return list of all products")
    void shouldReturnAllProducts()throws Exception{
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

   @Test
    @DisplayName("GET/products/find-by-id?id=1 - must return product by id")
    void shouldReturnProductById()throws Exception{
        mockMvc.perform(get("/products/find-by-id").param("id","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
   }

   @Test
   @DisplayName("GET/products/find-by-id?id=9999999 - must return 404 code")
    void shouldReturn404Code()throws Exception{
        mockMvc.perform(get("/products/find-by-id").param("id","9999999"))
                .andExpect(status().isNotFound());
   }

   @Test
   @DisplayName("GET/products/search?name=jordan - must return list of products with the same name")
   void shouldReturnProductsByName()throws Exception{
        mockMvc.perform(get("/products/search").param("name","jordan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..name",everyItem(containsStringIgnoringCase("jordan"))));
   }

   @Test
   @DisplayName("GET/products/search?name=bread - must return empty list")
    void shouldReturnEmptySearchByName()throws Exception{
        mockMvc.perform(get("/products/search").param("name","bread"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(0)));
   }
}
