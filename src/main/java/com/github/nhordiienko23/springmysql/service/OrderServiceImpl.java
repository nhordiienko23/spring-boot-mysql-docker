package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.Order;
import com.github.nhordiienko23.springmysql.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> searchById(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> searchByProductId(Long productId) {
        return orderRepository.findByProductId(productId);
    }

    @Override
    public List<Order> showAll() {
        return orderRepository.findAll();
    }
}
