package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.agent.KnowledgeSeekerAgent;
import com.github.martinfrank.nethackagent.chatmemory.PersistentMemoryProvider;
import com.github.martinfrank.nethackagent.embedding.EmbeddingFactory;
import com.github.martinfrank.nethackagent.tools.wiki.WhiteListTool;
import com.github.martinfrank.nethackagent.tools.wiki.WikiTool;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class KnowHowAgent {

    private static final Logger logger = LoggerFactory.getLogger(KnowHowAgent.class);

    private final PersistentMemoryProvider memoryProvider;
    private final LlmModelService llmModelService;

    private final WikiTool wikiTool;

    @Autowired
    public KnowHowAgent(PersistentMemoryProvider memoryProvider, LlmModelService llmModelService, WikiTool wikiTool) {
        this.memoryProvider = memoryProvider;
        this.llmModelService = llmModelService;
        this.wikiTool = wikiTool;
    }

    public String runAgent() {
        ChatModel model = llmModelService.getChatModel();
        EmbeddingStoreContentRetriever retriever = createRetriever();

        KnowledgeSeekerAgent planAgent = AiServices.builder(KnowledgeSeekerAgent.class)
                .chatModel(model)
//                .chatMemory(new InMemoryChatMemory())
                .chatMemoryProvider(memoryProvider)
                .contentRetriever(retriever)
                .tools(List.of(
                        new WhiteListTool(),
                        wikiTool)//, new WikiPageScraperTool(llmModelService.getEmbeddingModel())
                ).build();

        String seekKnowhowRequest = """
                sammle solange knowhow bis du eine detaillierten Informationen darüber hast, welche Quests es im Kingdom
                of Loathing gibt. welche Quests sind optional, welche obligatorisch? Ein guter anfang ist die Start-seite
                des wikis 'https://wiki.kingdomofloathing.com/Main_Page'.
                Als Resultat sollst du eine Liste der gelesenen Wikiseiten erstellen und welchen nutzen du aus ihr ziehen konntest.
                """;
        Long chatId = 5L;
        String thePlan = planAgent.seekKnowhow(chatId, seekKnowhowRequest);
        return thePlan;
    }


    private EmbeddingStoreContentRetriever createRetriever() {
        EmbeddingModel embeddingModel = llmModelService.getEmbeddingModel();

        EmbeddingStore<TextSegment> store = EmbeddingFactory.createEmbeddingStore();

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.5)
                .build();
    }
}
