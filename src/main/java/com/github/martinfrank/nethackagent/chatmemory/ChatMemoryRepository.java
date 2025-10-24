package com.github.martinfrank.nethackagent.chatmemory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemoryRepository extends JpaRepository<ChatMemoryEntity, Long> {
}
