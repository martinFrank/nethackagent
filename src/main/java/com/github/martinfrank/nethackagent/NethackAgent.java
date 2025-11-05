
package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.agent.PlanAgent;
import com.github.martinfrank.nethackagent.chatmemory.PersistentMemoryProvider;
import com.github.martinfrank.nethackagent.embedding.EmbeddingFactory;
import com.github.martinfrank.nethackagent.embedding.WikiDocumentService;
import com.github.martinfrank.nethackagent.tools.LoginManager;
import com.github.martinfrank.nethackagent.tools.adventure.AdventureInfoTool;
import com.github.martinfrank.nethackagent.tools.inventory.EquipmentTool;
import com.github.martinfrank.nethackagent.tools.inventory.InventoryTool;
import com.github.martinfrank.nethackagent.tools.player.PlayerInfoTool;
import com.github.martinfrank.nethackagent.tools.quest.QuestListTool;
import com.github.martinfrank.nethackagent.tools.skill.SkillTool;
import com.github.martinfrank.nethackagent.tools.wiki.WikiTool;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NethackAgent {

    private static final Logger logger = LoggerFactory.getLogger(NethackAgent.class);

    private final PersistentMemoryProvider memoryProvider;
    private final LlmModelService llmModelService;

    private final WikiTool wikiTool;


    @Autowired
    public NethackAgent(PersistentMemoryProvider persistentMemoryProvider, LlmModelService llmModelService, WikiTool wikiTool) {
        this.memoryProvider = persistentMemoryProvider;
        this.llmModelService = llmModelService;
        this.wikiTool = wikiTool;
    }

    public String runAgent() {
        logger.info("runAgent");
        ChatModel model = llmModelService.getChatModel();
        EmbeddingStoreContentRetriever retriever = createRetriever();
        RetrievalAugmentor augmentor = createAugmentor(retriever);
        PlanAgent planAgent = AiServices.builder(PlanAgent.class)
                .chatModel(model)
//                .chatMemory(new InMemoryChatMemory())
                .chatMemoryProvider(memoryProvider)
//                .contentRetriever(retriever)
                .retrievalAugmentor(augmentor)
                .tools(List.of(
                        wikiTool,
                        new PlayerInfoTool(),
                        new InventoryTool(),
                        new EquipmentTool(),
                        new SkillTool(),
                        new AdventureInfoTool(),
                        new QuestListTool())//, new WikiPageScraperTool(llmModelService.getEmbeddingModel())
                ).build();

//        PlanValidator planValidator = AiServices.builder(PlanValidator.class)
//                .chatModel(model)
//                .build();

//        TaskExecutorValidator taskValidator = AiServices.builder(TaskExecutorValidator.class)
//                .chatModel(model)
//                .build();

        // Aufgabe: Plan erstellen
        String planRequest = """
                erstelle einen ausführlichen Plan um einen Spieler in Spiel 'Kingdom of loathing' zum Quest
                'Naughty Sorceress Quest' zu führen. dazu muss er vorher alle anderen quests vom Council of loathing
                beendet haben. Prüfe, welche Quests er bereits erledigt hat und welche Quests noch offen sind.
                Bestimme darauf basierend welche Quests noch der reihe nach abgearbeitet werden sollen.
                
                """;
        Long chatId = 3L;
        String thePlan = planAgent.createPlan(chatId, planRequest);

        logger.info("-------the plan-------");
        logger.info("{}", thePlan);

        // Erfolg prüfen
//        String isPlanValid = planValidator.isPlanSuccessful(new PlanValidationRequest(planRequest, thePlan).toJson());
//        logger.info("-------plan validation-------");
//        logger.info("plan is valid? {}", isPlanValid);

        LoginManager.logout();

        return thePlan;
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

    private EmbeddingStoreContentRetriever createRetriever() {
        EmbeddingModel embeddingModel = llmModelService.getEmbeddingModel();

        EmbeddingStore<TextSegment> store = EmbeddingFactory.createEmbeddingStore();

        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(store)
                .embeddingModel(embeddingModel)
                .maxResults(16)
                .minScore(0.3)
                .build();
    }

}
