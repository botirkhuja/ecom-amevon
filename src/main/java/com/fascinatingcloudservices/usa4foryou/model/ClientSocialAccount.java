package com.fascinatingcloudservices.usa4foryou.model;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client_social_accounts")
public class ClientSocialAccount {
    @Id
    private String id;
    private ClientDto client;
    private SocialPlatform platform;
    private String username;
    private String lowerCaseUsername;
    private Boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public enum SocialPlatform {
        INSTAGRAM, TELEGRAM, FACEBOOK, OTHER
    }

//    private void updateLowerCaseUserName() {
//        if (username != null) {
//            lowerCaseUsername = username.toLowerCase();
//        }
//    }
//
//    @PrePersist
//    protected void onCreate() {
//        this.id = RandomIdGenerator.generateRandomId(4);
//        this.createdAt = Timestamp.from(Instant.now());
//        updateLowerCaseUserName();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = Timestamp.from(Instant.now());
//        updateLowerCaseUserName();
//    }
}
