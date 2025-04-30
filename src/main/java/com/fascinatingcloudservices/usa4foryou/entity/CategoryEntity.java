package com.fascinatingcloudservices.usa4foryou.entity;

import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "categories")
public class CategoryEntity implements Persistable<String> {
    @Id
    private String categoryId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return categoryId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.isNew || categoryId == null ;
    }

}
