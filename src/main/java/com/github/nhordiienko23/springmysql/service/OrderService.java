package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.Order;

import java.util.List;

public interface OrderService {
    public List<Order> searchById(Long userId);

    public List<Order> searchByProductId(Long productId);

    public List<Order> showAll();
}
