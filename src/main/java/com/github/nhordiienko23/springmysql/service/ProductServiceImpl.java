package com.github.nhordiienko23.springmysql.service;

import com.github.nhordiienko23.springmysql.model.Product;
import com.github.nhordiienko23.springmysql.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> showAll() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<Product> findById(Long id) {
        return productRepository.findById(id).
                map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
