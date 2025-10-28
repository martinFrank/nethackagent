package com.github.martinfrank.nethackagent.chatmemory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PostgresChatMemoryStore implements ChatMemoryStore {


    private ChatMemoryRepository chatMemoryRepository;

    @Autowired
    public PostgresChatMemoryStore(ChatMemoryRepository chatMemoryRepository) {
        this.chatMemoryRepository = chatMemoryRepository;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        System.out.println("getMessages: memoryId="+memoryId);
        Optional<List<ChatMessage>> result =
         chatMemoryRepository.findById((Long) memoryId)//FIXME find by memoryID
                .map(entity -> {
                    try {
                        return objectMapper.readValue(
                                entity.getMessages(),
                                new TypeReference<List<ChatMessage>>() {} );
                    } catch (Exception e) {
//                        log.error("Failed to deserialize chat messages", e);
                        System.out.println("Failed to deserialize chat messages: "+e );
                        return Collections.emptyList();
                    }
                });
        return result.orElse(Collections.emptyList());
    }


    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        try {
            String json = objectMapper.writeValueAsString(messages);
            ChatMemoryEntity entity = new ChatMemoryEntity();
            entity.setMessages(json);
            chatMemoryRepository.save(entity);
        } catch (Exception e) {
            System.out.println("Failed to serialize chat messages: "+ e);
        }
    }

    @Override
    public void deleteMessages(Object memoryId) {
        chatMemoryRepository.deleteById((Long) memoryId);
    }


}
