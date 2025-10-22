package com.github.martinfrank.nethackagent.nethackagent;

import com.github.martinfrank.nethackagent.nethackagent.tools.KolAdventureSummaryTool;
import com.github.martinfrank.nethackagent.nethackagent.tools.KolCharacterSummaryTool;
import com.github.martinfrank.nethackagent.nethackagent.tools.KolQuestSummaryTool;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

import java.util.Arrays;
import java.util.List;

public class NethackAgent {

    private static final String OPENAI_API_KEY = "";
    static OpenAiChatModel model = OpenAiChatModel.builder()
            .apiKey(OPENAI_API_KEY)
            .modelName("gpt-4o") // oder z.B. "gpt-3.5-turbo"
//            .modelName("gpt-3.5-turbo") // oder z.B. "gpt-3.5-turbo"
            .build();

    public interface PlanningAgent {
        @SystemMessage("""
                Du bist ein Planungsagent, der Aufgaben selbstständig plant
                und verfügbare Tools verwenden kann, um fehlende Informationen aus dem Internet zu beschaffen.
                
                Verwende die bereitgestellten Tools, wenn sie für die Beantwortung der Benutzerfrage hilfreich sind.
                Wenn Informationen erforderlich sind, die du nicht kennst, suche sie mit den Tools, anstatt Annahmen zu treffen.
                
                Dein Ziel:
                1. Analysiere die Benutzerfrage.
                2. Wenn nötig, nutze Tools, um aktuelle Daten zu erhalten.
                3. Erstelle dann einen klaren, umsetzbaren Plan in nummerierten Schritten. Liste nur die Schritte auf, keine weitere Erklärungen sind nötig
                """)
        String createPlan(@UserMessage String frage);
    }

    public interface PlanResultValidator {
        @UserMessage("""
            Prüfe, ob folgende Antwort die Frage beantwortet und vollständig ist. 
            Die Frage befindet sich in ValidationRequest.question, die zu prüfende 
            befindet sich in ValidationRequest.answer. 
            
            Antworte nur mit true/false, denn die Ausgabe wird geparsed (Boolean.parse) 
            """)
        boolean isSuccessful(ValidationRequest request);
    }

    public static void main(String[] args) {

        // Tools bereitstellen
        List<Object> tools = Arrays.asList(
                new KolCharacterSummaryTool(),
                new KolAdventureSummaryTool(),
                new KolQuestSummaryTool());

        PlanningAgent planningAgent = AiServices.builder(PlanningAgent.class)
                .chatModel(model)
                .chatMemory(new MyChatMemory())
                .tools(tools)
                .build();

        PlanResultValidator validator = AiServices.builder(PlanResultValidator.class)
            .chatModel(model)
            .build();

        // Aufgabe: Plan erstellen
        String planRequest = """
            Du sollst für ein Spieler im Spiel "Kingdom of Loathing" die nächsten Schritte planen.
            Analysiere dazu den Spielerfortschritt und wähle aus, wo der nächste Kampf stattfinden
            soll. Falls es Tätigkeiten gibt, die vor dem Kampf durchgeführt werden sollen, so füge
            diese Schritte als Punkte in die Liste hinzu. Der Kampf sollte die letzte Aktion sein.
            gib nur die nummerierte Liste aus, füge keine Erklärung hinzu.
            """;
        String thePlan = planningAgent.createPlan(planRequest);
        System.out.println("-------the plan-------");
        System.out.println(thePlan);

        // Erfolg prüfen
        ValidationRequest validationRequest = new ValidationRequest(planRequest, thePlan);
        boolean isPlanValid = validator.isSuccessful(validationRequest);
        System.out.println("-------plan validation-------");
        System.out.println("plan is valid? "+isPlanValid);


    }
}
