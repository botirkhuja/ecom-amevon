package com.fascinatingcloudservices.usa4foryou.controller;
import com.fascinatingcloudservices.usa4foryou.model.Product;
import com.fascinatingcloudservices.usa4foryou.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getProducts();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        var createdProduct = service.save(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getById(@PathVariable String productId) {
        var optionalProduct = service.findById(productId);
        return optionalProduct
                .<ResponseEntity<Object>>map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Product is not found", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Object> updateById(@PathVariable String id, @Valid @RequestBody Product product) {
        var optionalProduct = service.findById(id);
        var savedProduct = service.save(product);
        return optionalProduct
                .map(product1 -> {
                    product.setProductId(id);
                    return service.save(product);
                })
                .<ResponseEntity<Object>>map(product1 -> new ResponseEntity<>(product1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Product is not found", HttpStatus.NOT_FOUND));
    }

    // Endpoint to search product by name and return productId
    @GetMapping("/search")
    public ResponseEntity<Object> searchByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean caseSensitive
    ) {

            var optionalProduct = service.findByName(name, caseSensitive);
            return optionalProduct
                    .<ResponseEntity<Object>>map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>("Product is not found", HttpStatus.NOT_FOUND));
    }
}