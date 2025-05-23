package com.fascinatingcloudservices.usa4foryou.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("brands")
public class BrandEntity implements Persistable<String> {
    @Id
    private String brandId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return this.brandId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.isNew;
    }
}
