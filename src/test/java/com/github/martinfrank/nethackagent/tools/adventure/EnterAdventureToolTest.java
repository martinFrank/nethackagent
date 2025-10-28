package com.github.martinfrank.nethackagent.tools.adventure;

import com.github.martinfrank.nethackagent.LoginManager;
import org.junit.jupiter.api.Test;

class EnterAdventureToolTest {

    @Test
    void testAdventureExecuteTool(){
        LoginManager.ensureLogin();
//        new EnterAdventureTool().enterFight("The Outskirts of Cobb's Knob");
        AdventureExecutionResult result = new AdventureExeutionTool().execute("The Sleazy Back Alley");
        System.out.println("result: "+result);

        LoginManager.logout();
    }

}