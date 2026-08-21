package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.Order;
import com.github.nhordiienko23.springmysql.repository.OrderRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<Order> searchById(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<Order> searchByProductId(Long productId) {
        return orderRepository.findByProductId(productId);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> showAll() {
        return orderRepository.findAll();
    }
}
