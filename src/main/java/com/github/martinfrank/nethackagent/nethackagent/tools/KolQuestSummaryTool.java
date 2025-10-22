package com.github.martinfrank.nethackagent.nethackagent.tools;

import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.persistence.QuestDatabase;
import net.sourceforge.kolmafia.request.LoginRequest;
import net.sourceforge.kolmafia.request.LogoutRequest;

import java.util.Arrays;
import java.util.List;

public class KolQuestSummaryTool {

    @Tool(
            name = "KolQuestSummaryTool"
            , value = """
            erzeugt eine Liste der Quests die im Spiel verfügbar sind.
            Verwende dieses Tool, um den Fortschritt der Quests des Spielers zu tracken
            """
    )
    public List<KolQuestSummary> execute() {
        System.out.println("execute KolQuestSummaryTool...");

        LoginRequest login = new LoginRequest(KolLoginData.USERNAME, KolLoginData.PASSWORD);

        login.run();

        List<KolQuestSummary> quests = Arrays.stream(QuestDatabase.Quest.values())
                .filter(q ->
                        q.getPref().startsWith("questL") //council
                        || q.getPref().startsWith("questG") //guild
                        || q.getPref().startsWith("questM") //market
                        || q.getPref().startsWith("questS") //sea
                ) //council
                .map(KolQuestSummaryTool::mapToSummary)
                .toList();

        LogoutRequest.getLastResponse();

        System.out.println("quests");
        quests.forEach(System.out::println);

        return quests;
    }

    private static KolQuestSummary mapToSummary(QuestDatabase.Quest quest) {
        KolQuestSummary summary = new KolQuestSummary();
        summary.setId(quest.toString());
        summary.setStarted(QuestDatabase.isQuestStarted(quest));
        summary.setFinished(QuestDatabase.isQuestFinished(quest));
        summary.setStatus(quest.getStatus());
        return summary;
    }

}
