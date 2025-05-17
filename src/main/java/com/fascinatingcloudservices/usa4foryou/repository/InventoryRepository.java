package com.fascinatingcloudservices.usa4foryou.repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;// contact_phone_number_id CHAR(6),
import org.springframework.stereotype.Repository;

import com.fascinatingcloudservices.usa4foryou.entity.InventoryEntity;

@Repository
public interface InventoryRepository extends ReactiveCrudRepository<InventoryEntity, Long> {
    // This is a placeholder for the actual implementation of the InventoryRepository.
    // You can add custom query methods here if needed.

}
