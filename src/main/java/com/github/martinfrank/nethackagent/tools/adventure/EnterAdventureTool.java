package com.github.martinfrank.nethackagent.tools.adventure;

import com.github.martinfrank.nethackagent.LoginManager;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.KoLAdventure;
import net.sourceforge.kolmafia.RequestThread;
import net.sourceforge.kolmafia.persistence.AdventureDatabase;
import net.sourceforge.kolmafia.request.LogoutRequest;


public class EnterAdventureTool {

    //FIXME wie in KolAdventureSummary als name bezeichnet
    @Tool(
            name = "EnterAdventureTool"
            , value = """
            Mit diesem Tool betritt ein Spieler das Abenteuer und schaltet somit den
            Kampfablauf frei. Ein Abenteuer kann nur verlassen werden, indem man
            gewinnt oder stirbt. Es kann mehrere Runden dauern, bis der Kampf vorbei ist.
            
            Der Parameter für dieses Tool ist der Name des Abenteuers.
            """
    )
    public String enterAdventure(String adventureName) {
        LoginManager.ensureLogin();

        KoLAdventure last = KoLAdventure.lastVisitedLocation();
        if(last != null){
            LogoutRequest.getLastResponse();
            return "you are already in a fight in adventure "+last.getAdventureName();
        }

        KoLAdventure adventure = AdventureDatabase.getAdventure(adventureName);
        System.out.println("you entered adventure: "+adventure.getAdventureName());
        RequestThread.postRequest(adventure);

        last = KoLAdventure.lastVisitedLocation();
        if(last.getAdventureName().equals(adventureName)){
            LogoutRequest.getLastResponse();
            return "you successfully entered adventure "+last.getAdventureName();
        }

        return "you could not enter the adventure "+adventureName;
    }
}
