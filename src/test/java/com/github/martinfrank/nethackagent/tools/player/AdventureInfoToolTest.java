package com.github.martinfrank.nethackagent.tools.player;

import com.github.martinfrank.nethackagent.tools.adventure.AdventureInfo;
import com.github.martinfrank.nethackagent.tools.adventure.AdventureInfoTool;

import java.util.List;


class AdventureInfoToolTest {

//    @Test
    void testAdventureSummary(){
        List<AdventureInfo> result = new AdventureInfoTool().getAdventures();
//        result.forEach(System.out::println);
    }

}