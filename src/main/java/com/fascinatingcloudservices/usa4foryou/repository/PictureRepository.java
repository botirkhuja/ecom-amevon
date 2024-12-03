package com.fascinatingcloudservices.usa4foryou.repository;

import com.fascinatingcloudservices.usa4foryou.model.Category;
import com.fascinatingcloudservices.usa4foryou.model.ClientAddress;
import com.fascinatingcloudservices.usa4foryou.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {
    List<Picture> findByProductProductId(String productId);
}
