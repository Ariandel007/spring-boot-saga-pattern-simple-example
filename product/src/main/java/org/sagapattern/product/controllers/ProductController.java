package org.sagapattern.product.controllers;

import org.sagapattern.product.models.Product;
import org.sagapattern.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @PostMapping(value = "")
    ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(this.productRepository.save(product));
    }
}
