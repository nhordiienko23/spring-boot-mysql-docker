package com.github.nhordiienko23.springmysql.controller;


import com.github.nhordiienko23.springmysql.model.Product;
import com.github.nhordiienko23.springmysql.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductRepository productRepository;
    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> showAll(){
        return productRepository.findAll();
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<Product> findById(@RequestParam Long id){
        return productRepository.findById(id).
                map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @GetMapping("/search")
    public List<Product> findByName(@RequestParam String name){
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
