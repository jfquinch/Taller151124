package com.demo.Tienda.controllers;

import com.demo.Tienda.entities.ProductEntity;
import com.demo.Tienda.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProducts(@PathVariable UUID id){
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<?> createProducts(@RequestBody ProductEntity product){
        return productService.createProducts(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(
            @PathVariable UUID id, @RequestBody ProductEntity product) {
        return productService.updateProducts(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable UUID id){
        return productService.deleteProduct(id);
    }
}
