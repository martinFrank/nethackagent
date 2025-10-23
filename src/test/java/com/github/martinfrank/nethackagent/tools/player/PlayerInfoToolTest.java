package com.github.martinfrank.nethackagent.tools.player;

class PlayerInfoToolTest {

//        @Test
    void testAdventureSummary(){
        PlayerInfo result = new PlayerInfoTool().getPlayerInfo();
        System.out.println(result);
    }

}