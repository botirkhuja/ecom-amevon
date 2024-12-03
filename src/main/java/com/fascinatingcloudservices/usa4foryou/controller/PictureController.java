package com.fascinatingcloudservices.usa4foryou.controller;

import com.fascinatingcloudservices.usa4foryou.model.Picture;
import com.fascinatingcloudservices.usa4foryou.model.Product;
import com.fascinatingcloudservices.usa4foryou.service.PictureService;
import com.fascinatingcloudservices.usa4foryou.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products/{productId}/pictures")
public class PictureController {

    private final PictureService pictureService;
    private final ProductService productService;

    public PictureController(PictureService pictureService, ProductService productService) {
        this.pictureService = pictureService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Picture>> getAllByClientId(@PathVariable String productId) {
        List<Picture> notes = pictureService.findAllByProductId(productId);
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@PathVariable String productId, @RequestBody Picture address) {
        Optional<Product> productOptional = productService.findById(productId);

        if (productOptional.isEmpty()) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        Product product = productOptional.get();
        address.setProduct(product);  // Set the Client entity, not productId directly
        try {
            Picture saved = pictureService.save(address);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}