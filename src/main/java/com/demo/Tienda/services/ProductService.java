package com.demo.Tienda.services;

import com.demo.Tienda.entities.ProductEntity;
import com.demo.Tienda.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Map<String, Object>> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        List<ProductEntity> product = productRepository.findAll();
        response.put("Countries", product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getProductById(UUID id) {
        Map<String, Object> response = new HashMap<>();
        Optional<ProductEntity> productFound = productRepository.findById(id);
        if (productFound.isPresent()) {
            response.put("Product", productFound.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("Error", "Product not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Map<String, Object>> createProducts(ProductEntity product) {
        Map<String, Object> response = new HashMap<>();
        product.setId(UUID.randomUUID());
        if (productRepository.existsById(product.getId())) {
            response.put("Status", "Item already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            ProductEntity newCountry = productRepository.save(product);
            response.put("Status", "Item inserted successfully");
            response.put("Product", newCountry);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Map<String, Object>> updateProducts(UUID id, ProductEntity product) {
        Map<String, Object> response = new HashMap<>();
        Optional<ProductEntity> productFound = productRepository.findById(id);
        if (productFound.isPresent()) {
            ProductEntity existingProduct = productFound.get();
            existingProduct.setNombreProducto(product.getNombreProducto());
            existingProduct.setCategoria(product.getCategoria());
            existingProduct.setPrecio(product.getPrecio());
            productRepository.save(existingProduct);
            response.put("Status", "Product updated successfully");
            response.put("UpdatedProduct", existingProduct);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("Status", "Product not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteProduct(UUID id) {
        Map<String, Object> response = new HashMap<>();
        Optional<ProductEntity> productFound = productRepository.findById(id);
        if (productFound.isPresent()) {
            productRepository.deleteById(id);
            response.put("Status", "Product deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("Status", "Product not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
