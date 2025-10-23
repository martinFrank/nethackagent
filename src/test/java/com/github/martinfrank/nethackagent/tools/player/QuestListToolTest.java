package com.github.martinfrank.nethackagent.tools.player;

import com.github.martinfrank.nethackagent.tools.quest.Quest;
import com.github.martinfrank.nethackagent.tools.quest.QuestListTool;

import java.util.List;

class QuestListToolTest {


//    @Test
    void testQuestTool(){
        List<Quest> quests = new QuestListTool().getQuests();
        quests.forEach(System.out::println);
    }


}