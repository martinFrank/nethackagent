package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.embedding.EmbeddingFactory;
import com.github.martinfrank.nethackagent.ollama.OllamaModelFactory;
import com.github.martinfrank.nethackagent.openapi.OpenApiModelFactory;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
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

    public RetrievalAugmentor createAugmentor(int maxResults, double minScore) {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(createRetriever(maxResults, minScore))
                .contentInjector((contentList, chatMessage) -> {
                    if(chatMessage instanceof UserMessage userMessage) {
                        StringBuilder prompt = new StringBuilder(userMessage.singleText());
                        prompt.append("\nPlease, only use the following information:\n");
                        contentList.forEach(content -> prompt.append("- ").append(content.textSegment().text()).append("\n"));
                        return new UserMessage(prompt.toString());
                    }
                    return chatMessage;
                })
                .build();
    }

    public EmbeddingStoreContentRetriever createRetriever(int maxResults, double minScore) {
        EmbeddingStore<TextSegment> store = EmbeddingFactory.createEmbeddingStore();
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(getEmbeddingModel())
                .maxResults(maxResults)
                .minScore(minScore)
                .build();
    }
}
