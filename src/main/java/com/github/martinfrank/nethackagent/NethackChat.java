package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.chatmemory.InMemoryChatMemory;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NethackChat {
    private final ConversationalRetrievalChain chain;
    private final ChatModel model;

    @Autowired
    public NethackChat(LlmModelService llmModelService) {
        model = llmModelService.getChatModel();
        RetrievalAugmentor augmentor = llmModelService.createAugmentor(12, 05.);
        chain = ConversationalRetrievalChain.builder()
                .chatModel(model)
                .chatMemory(new InMemoryChatMemory())
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

}
