package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    public List<Product> showAll();

    public ResponseEntity<Product> findById(Long id);

    List<Product> findByName(String name);

}
