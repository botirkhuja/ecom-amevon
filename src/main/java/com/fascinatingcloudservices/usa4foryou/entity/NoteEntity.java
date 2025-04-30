package com.fascinatingcloudservices.usa4foryou.entity;
import com.fascinatingcloudservices.usa4foryou.utils.RandomIdGenerator;
import jakarta.validation.constraints.NotNull;
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
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes")
public class NoteEntity implements Persistable<String> {
    @Id
    private String noteId;
    private String note;
    private String clientId;
    private String orderId;
    
    private Boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew || this.noteId == null;
    }

    @Override
    public String getId() {
        return noteId;
    }
}
