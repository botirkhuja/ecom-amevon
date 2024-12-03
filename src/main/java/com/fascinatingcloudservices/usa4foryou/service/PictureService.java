package com.fascinatingcloudservices.usa4foryou.service;

import com.fascinatingcloudservices.usa4foryou.model.Brand;
import com.fascinatingcloudservices.usa4foryou.model.Picture;
import com.fascinatingcloudservices.usa4foryou.repository.PictureRepository;
import com.fascinatingcloudservices.usa4foryou.utils.ExceptionCheckers;
import com.fascinatingcloudservices.usa4foryou.utils.RetryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PictureService {

    @Autowired
    PictureRepository repo;

    public List<Picture> findAll() {
        return repo.findAll();
    }

    public Optional<Picture> findById(String id) {
        return repo.findById(id);
    }

    public List<Picture> findAllByProductId(String productId) {
        return repo.findByProductProductId(productId);
    }

    public Picture save(Picture picture) {
        return RetryUtils.retry(() -> {
            picture.setId(new Picture().getId());
            return repo.save(picture);
        });
    }
}
