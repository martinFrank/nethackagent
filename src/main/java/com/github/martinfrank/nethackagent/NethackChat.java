package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.embedding.EmbeddingFactory;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;


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

        EmbeddingStore store = EmbeddingFactory.createEmbeddingStore();

        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.5)
                .build();

        // (2) Eigener PromptTemplate für System- und User-Prompt
        PromptTemplate template = PromptTemplate.from(
                "System: Du bist ein hilfreicher AI-Assistent.\n"
                        + "Kontext: {context}\n"
                        + "Vergangene Unterhaltung: {chat_history}\n"
                        + "User: {question}\n"
        );

//        DefaultRetrievalAugmentor augmentor = DefaultRetrievalAugmentor.builder()
//                .contentRetriever(retriever)
//                .
//                .promptTemplate(template)
//                .build();

        // (5) ConversationalRetrievalChain bauen
        ConversationalRetrievalChain chain = ConversationalRetrievalChain.builder()
                .chatModel(model)
//                .retrievalAugmentor(augmentor)
                .chatMemory(new MyChatMemory())
                .contentRetriever(retriever)
                .build();

        // (6) Nutzeranfrage ausführen
//        String antwort = chain.execute("welche gegenstände von Kingdom of loathing kennst du? liste nur items auf die aus dem embedding her kennst. Die liste sollte min 20 items enthalten");
        String antwort = chain.execute("erkläre mir, was man alles tun muss im in Kingdom of Loathing zur Ascension zu kommen? nutze nur informationen aus dem embedding.");
//        String antwort = chain.execute("welche gegenstände geben denn viel feuerschaden? erkläre mir nur sachen, die du aus dem embedding her kennst");
        System.out.println(antwort);
    }

}
