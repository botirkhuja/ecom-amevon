package com.fascinatingcloudservices.usa4foryou.model;

import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_pictures")
public class Picture {
    @Id
    @Column(name = "picture_id", nullable = false, unique = true, columnDefinition = "CHAR(36)")
    private String id;

//    @Column(name = "product_id", nullable = false, unique = true, columnDefinition = "CHAR(36)")
//    private String productId;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @NotNull(message = "Url is mandatory")
    @Size(min = 1, message = "Url must be at least 1 character")
    @Column(name = "url", nullable = false)
    private String url;

    private String description;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    // Automatically set timestamps on creation and update
    @PrePersist
    protected void onCreate() {
        this.id = RandomIdGenerator.generateRandomId(6);
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
