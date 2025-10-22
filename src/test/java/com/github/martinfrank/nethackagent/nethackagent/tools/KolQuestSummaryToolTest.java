package com.github.martinfrank.nethackagent.nethackagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KolQuestSummaryToolTest {


//    @Test
    void testQuestTool(){
        List<KolQuestSummary> quests = new KolQuestSummaryTool().execute();
        quests.forEach(System.out::println);
    }


}