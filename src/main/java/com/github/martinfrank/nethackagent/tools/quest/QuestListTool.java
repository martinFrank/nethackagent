package com.github.martinfrank.nethackagent.tools.quest;

import com.github.martinfrank.nethackagent.tools.LoginManager;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.persistence.QuestDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class QuestListTool {

    private static final Logger logger = LoggerFactory.getLogger(QuestListTool.class);

    @Tool(
            name = "KolQuestListTool"
            , value = """
            erzeugt eine Liste der Quests die für den Spieler verfügbar sind.
            Verwende dieses Tool, wenn du wissen willst, welche Quests der
            Spieler schon erledigt hat und welche Quests noch offen sind.
            """
    )
    public List<Quest> getQuests() {
        logger.info("using QuestListTool.getQuests()");

        LoginManager.ensureLogin();

        List<Quest> quests = Arrays.stream(QuestDatabase.Quest.values())
                .filter(q ->
                        q.getPref().startsWith("questL") //council
                                || q.getPref().startsWith("questG") //guild
                                || q.getPref().startsWith("questM") //market
                                || q.getPref().startsWith("questS") //sea
                ) //council
                .map(QuestListTool::mapToQuest)
                .toList();

        quests.forEach(quest -> logger.debug(quest.toString()));

        return quests;
    }

    private static Quest mapToQuest(QuestDatabase.Quest quest) {
        Quest summary = new Quest();
        summary.setId(quest.toString());
        summary.setName(QuestDatabase.prefToTitle(quest.getPref()));
        summary.setStarted(QuestDatabase.isQuestStarted(quest));
        summary.setFinished(QuestDatabase.isQuestFinished(quest));
        summary.setStatus(quest.getStatus());
        return summary;
    }

}
