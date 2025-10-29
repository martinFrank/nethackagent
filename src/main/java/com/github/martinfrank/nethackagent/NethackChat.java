package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.embedding.EmbeddingFactory;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;

import java.util.Scanner;


public class NethackChat {

    public static void main(String[] args) {
        new NethackChat().runChat();
    }

    public void runChat() {

        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(OpenAiConfig.OPENAI_API_KEY)
                .modelName("gpt-4o") // oder z.B. "gpt-3.5-turbo"
//            .modelName("gpt-3.5-turbo") // oder z.B. "gpt-3.5-turbo"
                .build();

        OpenAiEmbeddingModel embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(OpenAiConfig.OPENAI_API_KEY)
                .modelName("text-embedding-3-small")
                .build();

        EmbeddingStore<TextSegment> store = EmbeddingFactory.createEmbeddingStore();

        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.5)
                .build();

        ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
                .chatModel(model)
                .chatMemory(new MyChatMemory())
                .contentRetriever(retriever)
                .build();

        Scanner scanner = new Scanner(System.in);
        String request;
        while(true){
            request = scanner.nextLine();
            if("exit".equalsIgnoreCase(request)){
                break;
            }

            String strict = " Nutze nur Informationen aus dem embedding.";
//            String antwort = chain.execute("erkläre mir, was man alles tun muss im in Kingdom of Loathing zur Ascension zu kommen? nutze nur informationen aus dem embedding.");
            String antwort = chain.execute(request+strict);
            System.out.println(antwort);
        }

    }

}
