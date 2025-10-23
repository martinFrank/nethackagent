package com.github.martinfrank.nethackagent.tools.quest;

import com.github.martinfrank.nethackagent.LoginManager;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.persistence.QuestDatabase;

import java.util.Arrays;
import java.util.List;

public class QuestListTool {

    @Tool(
            name = "KolQuestListTool"
            , value = """
            erzeugt eine Liste der Quests die für den Spieler verfügbar sind.
            Verwende dieses Tool, wenn du wissen willst, welche Quests der
            Spieler schon erledigt hat und welche Quests noch offen sind.
            """
    )
    public List<Quest> getQuests() {
        System.out.println("listQuests()");

        LoginManager.ensureLogin();

        List<Quest> quests = Arrays.stream(QuestDatabase.Quest.values())
                .filter(q ->
                        q.getPref().startsWith("questL") //council
                        || q.getPref().startsWith("questG") //guild
                        || q.getPref().startsWith("questM") //market
                        || q.getPref().startsWith("questS") //sea
                ) //council
                .map(QuestListTool::mapToSummary)
                .toList();

        System.out.println("quests");
        quests.forEach(System.out::println);

        return quests;
    }

    private static Quest mapToSummary(QuestDatabase.Quest quest) {
        Quest summary = new Quest();
        summary.setId(quest.toString());
        summary.setStarted(QuestDatabase.isQuestStarted(quest));
        summary.setFinished(QuestDatabase.isQuestFinished(quest));
        summary.setStatus(quest.getStatus());
        return summary;
    }

}
