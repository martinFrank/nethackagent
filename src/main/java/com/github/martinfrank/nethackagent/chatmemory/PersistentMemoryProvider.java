package com.github.martinfrank.nethackagent.chatmemory;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistentMemoryProvider implements ChatMemoryProvider {

    private final PostgresChatMemoryStore store;

    @Autowired
    public PersistentMemoryProvider(PostgresChatMemoryStore store) {
        this.store = store;
    }

    @Override
    public ChatMemory get(Object memoryId) {
        return MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .chatMemoryStore(store)
                .build();
    }
}
