package com.github.martinfrank.nethackagent.tools.adventure;

class EnterAdventureToolTest {

//    @Test
    void testEnterAdventure(){
//        new EnterAdventureTool().enterFight("The Outskirts of Cobb's Knob");
        String result = new EnterAdventureTool().enterAdventure("The Sleazy Back Alley");
        System.out.println("result: "+result);
    }

}