package com.github.martinfrank.nethackagent.chatmemory;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMemoryRepository extends JpaRepository<ChatMemoryEntity, Long> {

}
