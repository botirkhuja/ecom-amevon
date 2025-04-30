package com.fascinatingcloudservices.usa4foryou.entity;

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

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "pictures")
public class Picture implements Persistable<String> {
    @Id
    private String pictureId;

    private String productId;
    private String storeId;

    // @NotNull(message = "Url is mandatory")
    // @Size(min = 1, message = "Url must be at least 1 character")
    private String url;
    private Integer orderNumber;
    private Boolean isPrimary;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Boolean isDeleted;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return this.pictureId;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.isNew;
    }
}
