package com.github.martinfrank.nethackagent;

import com.github.martinfrank.nethackagent.agent.PlanAgent;
import com.github.martinfrank.nethackagent.chatmemory.PersistentMemoryProvider;
import com.github.martinfrank.nethackagent.embedding.EmbeddingFactory;
import com.github.martinfrank.nethackagent.tools.adventure.AdventureInfoTool;
import com.github.martinfrank.nethackagent.tools.inventory.EquipmentTool;
import com.github.martinfrank.nethackagent.tools.inventory.InventoryTool;
import com.github.martinfrank.nethackagent.tools.player.PlayerInfoTool;
import com.github.martinfrank.nethackagent.tools.quest.QuestListTool;
import com.github.martinfrank.nethackagent.tools.skill.SkillTool;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NethackAgent {

    private final PersistentMemoryProvider memoryProvider;

    @Autowired
    public NethackAgent(PersistentMemoryProvider persistentMemoryProvider) {
        this.memoryProvider = persistentMemoryProvider;
    }

    public void runAgent() {

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

        PlanAgent planAgent = AiServices.builder(PlanAgent.class)
                .chatModel(model)
                .chatMemoryProvider(memoryProvider)
                .contentRetriever(retriever)
                .tools(List.of(
                        new PlayerInfoTool(),
                        new InventoryTool(),
                        new EquipmentTool(),
                        new SkillTool(),
                        new AdventureInfoTool(),
                        new QuestListTool()))
                .build();

//        PlanValidator planValidator = AiServices.builder(PlanValidator.class)
//                .chatModel(model)
//                .build();

//        TaskExecutorValidator taskValidator = AiServices.builder(TaskExecutorValidator.class)
//                .chatModel(model)
//                .build();

        // Aufgabe: Plan erstellen
        String planRequest = """
                Du sollst für einen Spieler von "Kingdom of Loathing" den Weg zur Ascension begleiten.
                Bestimme dazu, welches quest aktuell verfolgt werden soll und welche Location (adventure)
                dazu besucht werden soll. Prüfe vorab, wie weit der Spieler bisher gekommen ist, um eine.
                passende Auswahl zu treffen.
                """;
        Long chatId = 2L;
        String thePlan = planAgent.createPlan(chatId, planRequest);


        System.out.println("-------the plan-------");
        System.out.println(thePlan);

        // Erfolg prüfen
//        String isPlanValid = planValidator.isPlanSuccessful(new PlanValidationRequest(planRequest, thePlan).toJson());
//        System.out.println("-------plan validation-------");
//        System.out.println("plan is valid? " + isPlanValid);


        LoginManager.logout();

    }
}
