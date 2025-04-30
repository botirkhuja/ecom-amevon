package com.fascinatingcloudservices.usa4foryou.repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.fascinatingcloudservices.usa4foryou.entity.DiscountEntity;

@Repository
public interface DiscountRepository extends ReactiveCrudRepository<DiscountEntity, String> {
    // This is a placeholder for the actual implementation of the DiscountRepository.
    // You can add custom query methods here if needed.

}
