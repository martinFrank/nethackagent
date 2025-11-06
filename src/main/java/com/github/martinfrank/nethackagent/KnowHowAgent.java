package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.agent.KnowledgeSeekerAgent;
import com.github.martinfrank.nethackagent.chatmemory.PersistentMemoryProvider;
import com.github.martinfrank.nethackagent.tools.wiki.WhiteListTool;
import com.github.martinfrank.nethackagent.tools.wiki.WikiTool;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
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
        EmbeddingStoreContentRetriever retriever = llmModelService.createRetriever(5, 0.5);

        KnowledgeSeekerAgent planAgent = AiServices.builder(KnowledgeSeekerAgent.class)
                .chatModel(model)
                .chatMemoryProvider(memoryProvider)
                .contentRetriever(retriever)
                .tools(List.of(
                        new WhiteListTool(),
                        wikiTool)
                ).build();

        String seekKnowhowRequest = """
                sammle solange knowhow bis du eine detaillierten Informationen darüber hast, welche Quests es im Kingdom
                of Loathing gibt. welche Quests sind optional, welche obligatorisch? Ein guter anfang ist die Start-seite
                des wikis 'https://wiki.kingdomofloathing.com/Main_Page'.
                Als Resultat sollst du eine Liste der gelesenen Wikiseiten erstellen und welchen nutzen du aus ihr ziehen konntest.
                """;
        Long chatId = 5L;
        return planAgent.seekKnowhow(chatId, seekKnowhowRequest);
    }

}
