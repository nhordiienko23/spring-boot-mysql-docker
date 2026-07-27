package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.model.Order;
import com.github.nhordiienko23.springmysql.repository.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public  OrderController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Operation(summary = "returns a list of orders by user id")
    @GetMapping("/by-user")
    public List<Order> searchById(@RequestParam Long userId){
        return orderRepository.findByUserId(userId);
    }

    @Operation(summary = "returns a list of orders by product id")
    @GetMapping("/by-product")
    public List<Order> searchByProductId(@RequestParam Long productId){
        return orderRepository.findByProductId(productId);
    }

    @Operation(summary = "returns a list of all orders")
    @GetMapping
    public List<Order> showAll(){
        return orderRepository.findAll();
    }
}
