package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.model.Order;
import com.github.nhordiienko23.springmysql.repository.OrderRepository;
import com.github.nhordiienko23.springmysql.service.OrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }


    @Operation(summary = "returns a list of orders by user id")
    @GetMapping("/by-user")
    public List<Order> searchById(@RequestParam Long userId) {
        return orderService.searchById(userId);
    }


    @Operation(summary = "returns a list of orders by product id")
    @GetMapping("/by-product")
    public List<Order> searchByProductId(@RequestParam Long productId) {
        return orderService.searchByProductId(productId);
    }


    @Operation(summary = "returns a list of all orders")
    @GetMapping
    public List<Order> showAll() {
        return orderService.showAll();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/debug-token")
    public ResponseEntity<?> getAccessToken(org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken authentication) {
        return ResponseEntity.ok(authentication);
    }
    
}
