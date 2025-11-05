package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.chatmemory.InMemoryChatMemory;
import com.github.martinfrank.nethackagent.embedding.EmbeddingFactory;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NethackChat {
    private final ConversationalRetrievalChain chain;
    private final ChatModel model;

    @Autowired
    public NethackChat(LlmModelService llmModelService) {
        model = llmModelService.getChatModel();
        EmbeddingModel embeddingModel = llmModelService.getEmbeddingModel();

        EmbeddingStore<TextSegment> store = EmbeddingFactory.createEmbeddingStore();

        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .maxResults(12)
                .minScore(0.3)
                .build();

        RetrievalAugmentor augmentor = createAugmentor(retriever);

        chain = ConversationalRetrievalChain.builder()
                .chatModel(model)
                .chatMemory(new InMemoryChatMemory())
//                .contentRetriever(retriever)
                .retrievalAugmentor(augmentor)
                .build();
    }

    public String askChain(String question) {
//        String strict = " Nutze nur Informationen aus dem embedding.";
//            String antwort = chain.execute("erkläre mir, was man alles tun muss im in Kingdom of Loathing zur Ascension zu kommen? nutze nur informationen aus dem embedding.");
        return chain.execute(question);
    }

    public String askModel(String question) {
//        String strict = " Nutze nur Informationen aus dem embedding.";
//            String antwort = chain.execute("erkläre mir, was man alles tun muss im in Kingdom of Loathing zur Ascension zu kommen? nutze nur informationen aus dem embedding.");

        return model.chat(question);
    }

    private RetrievalAugmentor createAugmentor(ContentRetriever retriever) {
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(retriever)
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
}
