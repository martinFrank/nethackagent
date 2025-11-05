package com.github.martinfrank.nethackagent.chatmemory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatMemoryRepository extends JpaRepository<ChatMemoryEntity, Long> {

    @Transactional
    @Query("SELECT m FROM ChatMemoryEntity m WHERE m.memory_id = :memoryId")
    List<ChatMemoryEntity> getAllByMemoryId(Long memoryId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ChatMemoryEntity m WHERE m.memory_id = :memoryId")
    void deleteAllByMemoryId(Long memoryId);

}
