package com.github.martinfrank.nethackagent.chatmemory;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_memory")
public class ChatMemoryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "memory_id")
    private Long memory_id;

    @Column (name = "message_type")
    private String message_type;

    @Column(name = "message")
    private String message;

    @Column (name = "updated_at")
    private LocalDateTime updated_at = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemoryId() {
        return memory_id;
    }

    public void setMemoryId(Long memory_id) {
        this.memory_id = memory_id;
    }

    public String getMessageType() {
        return message_type;
    }

    public void setMessageType(String messageType) {
        this.message_type = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "ChatMemoryEntity{" +
                "id=" + id +
                ", memory_id=" + memory_id +
                ", message_type=" + message_type +
                ", message='" + message + '\'' +
                ", updated_at=" + updated_at +
                '}';
    }
}
