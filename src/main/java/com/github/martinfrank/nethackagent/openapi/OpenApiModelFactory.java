package com.github.martinfrank.nethackagent.openapi;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class OpenApiModelFactory {

    public EmbeddingModel createEmbeddingModel() {
        return OpenAiEmbeddingModel.builder()
                .apiKey(OpenAiConfig.OPENAI_API_KEY)
                .modelName("text-embedding-3-small")
                .build();
    }


    public OpenAiChatModel createChaModel() {
        return OpenAiChatModel.builder()
                .apiKey(OpenAiConfig.OPENAI_API_KEY)
                .modelName("gpt-4o") // oder z.B. "gpt-3.5-turbo"
//            .modelName("gpt-3.5-turbo") // oder z.B. "gpt-3.5-turbo"
                .build();

    }

}
