package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sub_categories")
public class SubCategory {
    @Id
    @Column(name = "sub_category_id",nullable = false, unique = true, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "category_id",nullable = false, unique = true, columnDefinition = "CHAR(36)")
    private String categoryId;

    @NotNull(message = "Name is mandatory")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lower_case_name", nullable = false)
    private String lowerCaseName;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    private void updateLowerCaseName() {
        if (this.name != null) {
            this.lowerCaseName = this.name.toLowerCase();
        }
    }


    @PrePersist
    protected void onCreate() {
        this.id = RandomIdGenerator.generateRandomId(3);
        this.createdAt = Timestamp.from(Instant.now());
        updateLowerCaseName();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
        updateLowerCaseName();
    }
}
