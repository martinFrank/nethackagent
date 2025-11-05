package com.github.martinfrank.nethackagent.chatmemory;

import com.github.martinfrank.nethackagent.tools.wiki.WikiTool;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.memory.ChatMemory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryChatMemory implements ChatMemory {


    private static final Logger logger = LoggerFactory.getLogger(WikiTool.class);

    private final String id = UUID.randomUUID().toString();

    private final List<ChatMessage> messages = new ArrayList<>();

    @Override
    public Object id() {
        return id;
    }

    @Override
    public void add(ChatMessage chatMessage) {
        logger.info("adding message {}", chatMessage);
        messages.add(chatMessage);
    }

    @Override
    public List<ChatMessage> messages() {
        return messages;
    }

    @Override
    public void clear() {
        messages.clear();
    }
}
