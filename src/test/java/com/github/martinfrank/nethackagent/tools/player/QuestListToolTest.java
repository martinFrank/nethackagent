package com.github.martinfrank.nethackagent.tools.player;

import com.github.martinfrank.nethackagent.LoginManager;
import com.github.martinfrank.nethackagent.tools.quest.Quest;
import com.github.martinfrank.nethackagent.tools.quest.QuestListTool;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuestListToolTest {


//    @Test
    void testQuestTool(){
        LoginManager.ensureLogin();
        List<Quest> quests = new QuestListTool().getQuests();
        LoginManager.logout();
    }


}