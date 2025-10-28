package com.github.martinfrank.nethackagent.chatmemory;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_memory")
public class ChatMemoryEntity {

    @Id
    @Column (name = "memory_id")
    private Long id;

    @Lob
    @Column(columnDefinition = "jsonb")
    private String messages;

    private LocalDateTime updatedAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ChatMemoryEntity{" +
                "id=" + id +
                ", messages='" + messages + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
