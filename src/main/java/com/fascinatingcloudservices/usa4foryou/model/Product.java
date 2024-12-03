package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product  {

    @Id
    @Column(name = "product_id", nullable = false, unique = true, columnDefinition = "CHAR(36)")
    private String productId;

    @NotNull(message = "Name is mandatory")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lower_case_name", nullable = false)
    private String lowerCaseName;

    @Size(max = 65535, message = "Description must be less than 65535 characters")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "category_id", columnDefinition = "CHAR(36)")
    private String categoryId;

    @Column(name = "sub_category_id", columnDefinition = "CHAR(36)")
    private String subCategoryId;

    @Column(name = "brand_id", columnDefinition = "CHAR(36)")
    private String brandId;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private List<Picture> pictures;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
    // Constructor
    public Product() {
        this.productId = RandomIdGenerator.generateRandomId(8);
    }

    private void updateLowerCaseName() {
        if (this.name != null) {
            this.lowerCaseName = this.name.toLowerCase();
        }
    }

    // Automatically set timestamps on creation and update
    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.from(Instant.now());
        updateLowerCaseName();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
        updateLowerCaseName();
    }
}
