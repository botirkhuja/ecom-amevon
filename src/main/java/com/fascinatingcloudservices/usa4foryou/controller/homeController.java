package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.model.Product;
import com.fascinatingcloudservices.usa4foryou.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class homeController {

    ProductService productService;

    public homeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    List<Product> mainPage () {
        return productService.getProducts();
    }
}
