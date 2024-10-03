package com.example.monkecommerce.controller;

import com.example.monkecommerce.dto.ProductRequestDto;
import com.example.monkecommerce.entity.Product;
import com.example.monkecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

        @Autowired
        private ProductRepository productRepository;

        @PostMapping
        public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDto productRequestDTO) {
            Product product = new Product();
            product.setName(productRequestDTO.getName());
            product.setDescription(productRequestDTO.getDescription());
            product.setPrice(productRequestDTO.getPrice());

            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        }
    }
