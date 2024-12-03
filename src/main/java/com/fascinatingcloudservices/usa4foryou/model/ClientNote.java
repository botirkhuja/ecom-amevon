package com.fascinatingcloudservices.usa4foryou.model;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "client_notes")
public class ClientNote {
    @Id
    @Column(name = "client_note_id", nullable = false, unique = true, columnDefinition = "CHAR(36)", length = 36)
    private String id;

//    @Column(name = "client_id", nullable = false, length = 36)
//    private String clientId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @NotNull(message = "Note is mandatory")
    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        this.id = RandomIdGenerator.generateRandomId(5);
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
