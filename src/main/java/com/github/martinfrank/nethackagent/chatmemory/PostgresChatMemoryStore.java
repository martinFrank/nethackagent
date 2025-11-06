package com.github.martinfrank.nethackagent.chatmemory;

import com.google.gson.Gson;
import dev.langchain4j.data.message.*;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostgresChatMemoryStore implements ChatMemoryStore {


    private static final Logger logger = LoggerFactory.getLogger(PostgresChatMemoryStore.class);

    private final ChatMemoryRepository chatMemoryRepository;

    private static final String AI = "AI";
    private static final String SYSTEM = "SYSTEM";
    private static final String USER = "USER";
    private static final String TOOL_EXECUTION_RESULT = "TOOL_EXECUTION_RESULT";

    private static final Gson GSON = new Gson();

    @Autowired
    public PostgresChatMemoryStore(ChatMemoryRepository chatMemoryRepository) {
        this.chatMemoryRepository = chatMemoryRepository;
    }

    @Transactional
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        logger.debug("get messages by memoryId {}",memoryId);
        List<ChatMemoryEntity> entities = chatMemoryRepository.getAllByMemoryId((Long) memoryId);
        return entities.stream().map(PostgresChatMemoryStore::fromEntity).toList();
    }

    private static ChatMessage fromEntity(ChatMemoryEntity entity) {
        return switch (entity.getMessageType()) {
            case AI -> GSON.fromJson(entity.getMessage(), AiMessage.class);
            case SYSTEM -> GSON.fromJson(entity.getMessage(), SystemMessage.class);
            case USER -> new UserMessage(entity.getMessage());
            case TOOL_EXECUTION_RESULT -> GSON.fromJson(entity.getMessage(), ToolExecutionResultMessage.class);
            default -> {logger.warn("unknown message type {}", entity.getMessageType());
                throw new IllegalArgumentException("unknown message type");
            }
        };
    }



    @Transactional //weil ZWEI transaktion stattfinden, delete und insert
    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        logger.debug("update messages by memoryId {}",memoryId);

        logger.debug("deleteAllByMemoryId  {}",memoryId);
        chatMemoryRepository.deleteAllByMemoryId((Long)memoryId);
        List<ChatMemoryEntity> entities = messages.stream().map(cm -> toEntity(memoryId, cm)).toList();

        logger.debug("saveAll");
        chatMemoryRepository.saveAll(entities);
    }

    private ChatMemoryEntity toEntity(Object memoryId, ChatMessage cm) {
        if(cm instanceof AiMessage aiMessage){
            ChatMemoryEntity entity = new ChatMemoryEntity();
            entity.setMemoryId((Long)memoryId);
            entity.setMessageType(AI);
            entity.setMessage(GSON.toJson(aiMessage));
            return entity;
        }
        if(cm instanceof SystemMessage systemMessage){
            ChatMemoryEntity entity = new ChatMemoryEntity();
            entity.setMemoryId((Long)memoryId);
            entity.setMessageType(SYSTEM);
            entity.setMessage(GSON.toJson(systemMessage));
            return entity;
        }
        if(cm instanceof UserMessage userMessage){
            ChatMemoryEntity entity = new ChatMemoryEntity();
            entity.setMemoryId((Long)memoryId);
            entity.setMessageType(USER);
            entity.setMessage(userMessage.singleText());
            return entity;
        }
        if(cm instanceof ToolExecutionResultMessage toolExecutionResultMessage){
            ChatMemoryEntity entity = new ChatMemoryEntity();
            entity.setMemoryId((Long)memoryId);
            entity.setMessageType(TOOL_EXECUTION_RESULT);
            entity.setMessage(GSON.toJson(toolExecutionResultMessage));
            return entity;
        }

        logger.warn("unknown message type {}", cm.type());
        throw new IllegalArgumentException("unknown Message type");
    }

    @Transactional
    @Override
    public void deleteMessages(Object memoryId) {
        chatMemoryRepository.deleteAllByMemoryId((Long)memoryId);
    }

}
