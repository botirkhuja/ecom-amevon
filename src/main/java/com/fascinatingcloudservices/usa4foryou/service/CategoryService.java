package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.Category;
import com.fascinatingcloudservices.usa4foryou.repository.CategoryRepository;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repo;

    public List<Category> findAll() {
        return repo.findAll();
    }

    public Optional<Category> findById(String id) {
        return repo.findById(id);
    }

    public Category save(Category category) {
        return RetryUtils.retry(() -> {
            category.setCategoryId(new Category().getCategoryId());
            return repo.save(category);
        });
    }
}
