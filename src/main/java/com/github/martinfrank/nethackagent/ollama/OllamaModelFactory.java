package com.github.martinfrank.nethackagent.ollama;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class OllamaModelFactory {
    private final OllamaChatProperties chatProps;
    private final OllamaEmbeddingProperties embedProps;

    @Autowired
    public OllamaModelFactory(OllamaChatProperties chatProps, OllamaEmbeddingProperties embedProps) {
        this.chatProps = chatProps;
        this.embedProps = embedProps;
    }
    public OllamaChatModel createChatModel() {
        return OllamaChatModel.builder()
                .baseUrl(chatProps.getBaseUrl())
                .modelName(chatProps.getModel())
                .temperature(chatProps.getTemperature())
                .timeout(Duration.ofMinutes(5))
                .build();
    }
    public OllamaEmbeddingModel createEmbeddingModel() {
        return OllamaEmbeddingModel.builder()
                .baseUrl(embedProps.getBaseUrl())
                .modelName(embedProps.getModel())
                .timeout(Duration.ofMinutes(5))
                .build();
    }
}
