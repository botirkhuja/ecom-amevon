package com.fascinatingcloudservices.usa4foryou.model;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
@Table(name = "client_social_accounts")
public class ClientSocialAccount {
    @Id
    @Column(name = "client_social_account_id", nullable = false, unique = true, columnDefinition = "CHAR(36)")
    private String id;

//    @Column(name = "client_id", nullable = false, length = 36)
//    private String clientId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    private SocialPlatform platform;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "lower_case_username", nullable = false, length = 255)
    private String lowerCaseUsername;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    public enum SocialPlatform {
        INSTAGRAM, TELEGRAM, FACEBOOK, OTHER
    }

    private void updateLowerCaseUserName() {
        if (username != null) {
            lowerCaseUsername = username.toLowerCase();
        }
    }

    @PrePersist
    protected void onCreate() {
        this.id = RandomIdGenerator.generateRandomId(4);
        this.createdAt = Timestamp.from(Instant.now());
        updateLowerCaseUserName();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
        updateLowerCaseUserName();
    }
}
