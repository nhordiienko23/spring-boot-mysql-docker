package com.github.nhordiienko23.springmysql.repository;

import com.github.nhordiienko23.springmysql.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByProductNameContainingIgnoreCase(String productName);
}
