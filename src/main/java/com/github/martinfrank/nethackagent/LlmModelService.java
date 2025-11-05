package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.ollama.OllamaModelFactory;
import com.github.martinfrank.nethackagent.openapi.OpenApiModelFactory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LlmModelService {

    @Value("${langchain4j.model.selector}")
    private String modelSelector;
    private final OllamaModelFactory ollamaModelFactory;
    private final OpenApiModelFactory openApiModelFactory;

    public LlmModelService(OllamaModelFactory ollamaModelFactory, OpenApiModelFactory openApiModelFactory) {
        this.ollamaModelFactory = ollamaModelFactory;
        this.openApiModelFactory = openApiModelFactory;
    }

    public EmbeddingModel getEmbeddingModel(){
        return switch (modelSelector){
            case "ollama" -> ollamaModelFactory.createEmbeddingModel();
            case "openapi" -> openApiModelFactory.createEmbeddingModel();
            default -> throw new IllegalArgumentException("unsupported model (not one of [ollama,openapi]");
        };
    }

    public ChatModel getChatModel(){
        return switch (modelSelector){
            case "ollama" -> ollamaModelFactory.createChatModel();
            case "openapi" -> openApiModelFactory.createChaModel();
            default -> throw new IllegalArgumentException("unsupported model (not one of [ollama,openapi]");
        };
    }
}
