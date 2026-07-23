package com.github.nhordiienko23.springmysql;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OrderControllerComponentTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET/orders - must return list of all orders")
    void shouldReturnAllOrders()throws Exception{
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("GET/orders/by-product?productId=1 - must return list of all orders with productId=1")
    void shouldReturnListOfOrdersByProductId() throws Exception{
        mockMvc.perform(get("/orders/by-product").param("productId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$..productId",everyItem(is(1))));
    }
    @Test
    @DisplayName("GET/orders/by-product?productId=123123 - must return empty list")
    void shouldReturnEmptyListByProductId()throws Exception{
        mockMvc.perform(get("/orders/by-product").param("productId","123123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(0)));
    }

    @Test
    @DisplayName("GET/orders/by-user?userId=6 - must return list of all orders with userId=6")
    void shouldReturnOrderByUserId()throws Exception{
        mockMvc.perform(get("/orders/by-user").param("userId","6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$..userId",everyItem(is(6))));
    }
    @Test
    @DisplayName("GET/orders/by-user?userId=99999999 - must return empty list")
    void shouldReturnEmptyListByUserId()throws Exception{
        mockMvc.perform(get("/orders/by-user").param("userId","99999999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(0)));
    }


}
