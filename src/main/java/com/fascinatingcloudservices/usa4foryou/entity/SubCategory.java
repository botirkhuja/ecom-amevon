package com.fascinatingcloudservices.usa4foryou.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "sub_categories")
public class SubCategory implements Persistable<String> {
    @Id
    private String subCategoryId;
    private String categoryId;

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean isDeleted;

    @Transient
    private boolean isNew;
    @Override
    public String getId() {
        return this.subCategoryId;
    }
    @Override
    @Transient
    public boolean isNew() {
        return this.isNew || subCategoryId == null;
    }

}
