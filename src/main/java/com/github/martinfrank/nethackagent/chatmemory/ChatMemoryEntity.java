package com.github.martinfrank.nethackagent.chatmemory;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_memory")
public class ChatMemoryEntity {
    @Id
    private Long memoryId;

    @Lob
    @Column(columnDefinition = "jsonb")
    private String messages;

    private LocalDateTime updatedAt = LocalDateTime.now();

    public Long getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(Long memoryId) {
        this.memoryId = memoryId;
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
                "memoryId=" + memoryId +
                ", messages='" + messages + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
