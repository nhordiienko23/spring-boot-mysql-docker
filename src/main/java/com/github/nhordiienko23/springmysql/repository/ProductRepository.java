package com.github.nhordiienko23.springmysql.repository;

import com.github.nhordiienko23.springmysql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
}
