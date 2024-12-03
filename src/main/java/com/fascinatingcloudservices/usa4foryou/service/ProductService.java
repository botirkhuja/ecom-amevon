package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.Picture;
import com.fascinatingcloudservices.usa4foryou.model.Product;
import com.fascinatingcloudservices.usa4foryou.repository.ProductRepo;
import com.fascinatingcloudservices.usa4foryou.utils.ExceptionCheckers;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Getter
@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Product save(Product product) {
        return RetryUtils.retry(() -> {
            product.setProductId(new Product().getProductId());
            return repo.save(product);
        });
    }

    public Optional<Product> findById(String productId) {
        return repo.findById(productId);
    }

    public Optional<Product> findByName(String productName, boolean caseSensitive) {
        if (caseSensitive) {
            return repo.findByName(productName);
        } else {
            return repo.findByNameContainingIgnoreCase(productName);
        }
    }
}
