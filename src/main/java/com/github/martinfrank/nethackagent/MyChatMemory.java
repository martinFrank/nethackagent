package com.github.martinfrank.nethackagent;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.memory.ChatMemory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyChatMemory implements ChatMemory {

    private final String id = UUID.randomUUID().toString();

    private final List<ChatMessage> messages = new ArrayList<>();

    @Override
    public Object id() {
        return id;
    }

    @Override
    public void add(ChatMessage chatMessage) {
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
