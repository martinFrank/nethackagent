package com.github.martinfrank.nethackagent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.martinfrank.nethackagent.tools.adventure.AdventureInfoTool;
import com.github.martinfrank.nethackagent.tools.player.PlayerInfoTool;
import com.github.martinfrank.nethackagent.tools.quest.QuestListTool;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NethackAgent {

    private static final String OPENAI_API_KEY = "";
    static OpenAiChatModel model = OpenAiChatModel.builder()
            .apiKey(OPENAI_API_KEY)
            .modelName("gpt-4o") // oder z.B. "gpt-3.5-turbo"
//            .modelName("gpt-3.5-turbo") // oder z.B. "gpt-3.5-turbo"
            .build();

    public interface PlanAgent {
        @SystemMessage("""
                Du bist ein Planungsagent, der Aufgaben selbstständig plant und verfügbare Tools
                verwenden kann, um fehlende Informationen aus dem Internet zu beschaffen.
                
                Verwende die bereitgestellten Tools, wenn sie für die Beantwortung der Benutzeraufgabe hilfreich sind.
                Wenn Informationen erforderlich sind, die du nicht kennst, suche sie mit den Tools, anstatt Annahmen zu treffen.
                
                Dein Ziel:
                1. Analysiere die Benutzeraufgabe.
                2. Wenn nötig, nutze Tools, um aktuelle Daten zu erhalten.
                3. Erstelle dann einen klaren, umsetzbaren Plan in nummerierten Schritten. Liste nur die Schritte auf, keine weitere Erklärungen sind nötig
                """)
        String createPlan(@UserMessage String aufgabe);
    }

    public interface TaskExecutorAgent {
        @SystemMessage("""
                Du bist ein Agent, der ein Computerspiel bedienen kann. Du bekommst Aufgaben, die für das
                Spiel "Kingdom of Loathing" erstellt sind.
                
                Dein Ziel:
                1. Analysiere die Benutzeraufgabe.
                2. Wenn nötig, nutze Tools, um um die Aufgabe durchzuführen.
                3. Liefere einen Bericht, wie gut die Ausführung der Aufgabe geklappt hat
                """)
        String executeTask(@UserMessage String aufgabe);
    }


    public interface PlanValidator {
        @SystemMessage("""
                Du bekommst einen Datenpaket, das aus einem entwickelte Plan der dazu gehörigen Aufgabe besteht.
                Deine Aufgabe ist es zu überprüfen, ob der Plan plausibel erscheint.
                
                Struktur des Datenpakets:
                {
                    "planRequest":"die Aufgabenstellung",
                    "planResult":"der entwickelte Plan"
                }
                
                Antworte nur mit true/false, denn die Ausgabe wird geparsed (Boolean.parse)
                """)
        String isPlanSuccessful(@UserMessage String requestJson);
    }


    //Agenten können keine Records verarbeiten
    public static class TaskExecutionValidationRequest {
        @JsonProperty public String task;
        @JsonProperty public String result;

        public TaskExecutionValidationRequest(String task, String result) {
            this.task = task;
            this.result = result;
        }
    }



    public interface TaskExecutorValidator {
        @UserMessage("""
                Prüfe, ob die Ausführung des Tasks erfolgreich war. Wurde im Task gar nichts
                gemacht, ist der Task erfolgreich.
                
                Task: TaskExecutionValidationRequest.task
                Ausführung: TaskExecutionValidationRequest.result
                
                Antworte nur mit true/false, denn die Ausgabe wird geparsed (Boolean.parse)
                """)
        boolean isTaskSuccessful(TaskExecutionValidationRequest request);
    }

    public record TaskExecution(String task, String result, boolean success) {
    }

    public static void main(String[] args) {

        PlanAgent planAgent = AiServices.builder(PlanAgent.class)
                .chatModel(model)
                .chatMemory(new MyChatMemory())
                .tools(Arrays.asList(
                        new PlayerInfoTool(),
                        new AdventureInfoTool(),
                        new QuestListTool()))
                .build();

        PlanValidator planValidator = AiServices.builder(PlanValidator.class)
                .chatModel(model)
                .build();

        TaskExecutorAgent taskExecutor = AiServices.builder(TaskExecutorAgent.class)
                .chatModel(model)
                .chatMemory(new MyChatMemory())
//                .tools(Arrays.asList(
//                        new KolCharacterSummaryTool(),
//                        new KolAdventureSummaryTool(),
//                        new KolQuestSummaryTool()))
                .build();

        TaskExecutorValidator taskValidator = AiServices.builder(TaskExecutorValidator.class)
                .chatModel(model)
                .build();

        // Aufgabe: Plan erstellen
        String planRequest = """
                Du sollst für einen Spieler von "Kingdom of Loathing" des nächsten Abenteuer
                (kostet einen AdventurePoint) aussuchen. Prüfe, welche Abenteuer verfügbar
                sind und benenne das Abenteuer, das am besten zu den aktuellen Quests
                passt und am meisten Beute oder Erfahrung bringt.
                
                Die Liste soll so aussehen:
                - Welche Schritte können vorab durchgeführt um unnötigen Ballast abzuwerfen?
                - Welche Schritte können vorab durchgeführt werden um die Erfolgschancen zu verbessern?
                - das eine Abenteuer, dass betreten werden soll.
                
                Gib die Schritte als nummerierte Liste aus, füge keine Erklärung hinzu.
                """;
        String thePlan = planAgent.createPlan(planRequest);
        System.out.println("-------the plan-------");
        System.out.println(thePlan);



        // Erfolg prüfen
        String isPlanValid = planValidator.isPlanSuccessful(new PlanValidationRequest(planRequest, thePlan).toJson());
        System.out.println("-------plan validation-------");
        System.out.println("plan is valid? " + isPlanValid);

        List<TaskExecution> executions = new ArrayList<>();
        for (String task : thePlan.split("\n")) {
            System.out.println("-------working on Task-------");
            System.out.println(task);

//            String result = taskExecutor.executeTask(task);
//            boolean isTaskSuccessful = taskValidator.isTaskSuccessful(
//                    new TaskExecutionValidationRequest(task, result));
//            TaskExecution taskExecution = new TaskExecution(task, result, isTaskSuccessful);
//            executions.add(taskExecution);
            //FIXME stop, if one task failed
        }
//        executions.forEach(System.out::println);

        LoginManager.logout();
    }
}
