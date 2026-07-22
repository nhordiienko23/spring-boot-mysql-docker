package com.github.nhordiienko23.springmysql.controller;

import com.github.nhordiienko23.springmysql.model.Order;
import com.github.nhordiienko23.springmysql.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public  OrderController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @GetMapping("/by-user")
    public List<Order> searchById(@RequestParam long userId){
        return orderRepository.findByUserId(userId);
    }

    @GetMapping("/by-product")
    public List<Order> searchByProductName(@RequestParam Long productID){
        return orderRepository.findByProductId(productID);
    }

    @GetMapping
    public List<Order> showAll(){
        return orderRepository.findAll();
    }
}
