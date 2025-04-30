package com.fascinatingcloudservices.usa4foryou.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class ProductEntity implements Persistable<String>  {

    @Id
    private String productId;
    private String name;

    private String description;

    private Double price;

    private String categoryId;
    private String subCategoryId;
    private String brandId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return this.productId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.isNew;
    }
}
