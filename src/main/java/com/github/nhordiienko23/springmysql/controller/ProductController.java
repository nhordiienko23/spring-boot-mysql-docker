package com.github.nhordiienko23.springmysql.controller;


import com.github.nhordiienko23.springmysql.model.Product;
import com.github.nhordiienko23.springmysql.service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Operation(summary = "returns a list of all products")
    @GetMapping
    public List<Product> showAll() {
        return productService.showAll();
    }

    @Operation(summary = "returns a product  by id")
    @GetMapping("/find-by-id")
    public ResponseEntity<Product> findById(@RequestParam Long id) {
        return productService.findById(id);
    }

    @Operation(summary = "returns a list of products by name containing name ignoreCase")
    @GetMapping("/search")
    public List<Product> findByName(@RequestParam String name) {
        return productService.findByName(name);
    }
}
