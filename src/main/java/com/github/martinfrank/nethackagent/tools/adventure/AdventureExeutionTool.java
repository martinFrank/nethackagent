package com.github.martinfrank.nethackagent.tools.adventure;

import com.github.martinfrank.nethackagent.LoginManager;
import com.github.martinfrank.nethackagent.tools.inventory.Inventory;
import com.github.martinfrank.nethackagent.tools.inventory.InventoryTool;
import com.github.martinfrank.nethackagent.tools.inventory.Item;
import com.github.martinfrank.nethackagent.tools.player.PlayerInfo;
import com.github.martinfrank.nethackagent.tools.player.PlayerInfoTool;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.KoLAdventure;
import net.sourceforge.kolmafia.RequestThread;
import net.sourceforge.kolmafia.persistence.AdventureDatabase;
import net.sourceforge.kolmafia.request.LogoutRequest;

import java.util.ArrayList;
import java.util.List;


public class AdventureExeutionTool {

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
    public AdventureExecutionResult execute(String adventureName) {
        LoginManager.ensureLogin();

        PlayerInfo playerBefore = new PlayerInfoTool().getPlayerInfo();
        Inventory inventoryBefore = new Inventory(new InventoryTool().getInventory());

        KoLAdventure adventure = AdventureDatabase.getAdventure(adventureName);
        System.out.println("you entered adventure: "+adventure.getAdventureName());
        RequestThread.postRequest(adventure);

        KoLAdventure last = KoLAdventure.lastVisitedLocation();
        if(last.getAdventureName().equals(adventureName)){
            LogoutRequest.getLastResponse();
            PlayerInfo playerAfter = new PlayerInfoTool().getPlayerInfo();
            Inventory inventoryAfter = new Inventory(new InventoryTool().getInventory());

            //FIXME effects
            // FIXME stats
            AdventureExecutionResult result = new AdventureExecutionResult();
            long deltaMoney = playerAfter.getAvailableMeat() - playerBefore.getAvailableMeat();
            long deltaHealth = playerAfter.getCurrentHp() - playerBefore.getCurrentHp();
            long deltaAdventures = playerAfter.getAdventuresLeft() - playerBefore.getAdventuresLeft();
            List<Item> addedItems = inventoryBefore.findAdded(inventoryAfter);
            List<Item> removedItems = inventoryBefore.findRemoved(inventoryAfter);

            result.setAddedItems(addedItems);
            result.setRemovedItems(removedItems);
            result.setDeltaHealth(deltaHealth);
            result.setDeltaMoney(deltaMoney);
            result.setAdventure(AdventureInfoTool.mapToSummary(last));
            result.setDeltaAdventures(deltaAdventures);
            result.setWasSuccess(true);
            System.out.println(result);
            return result;
        }

        return new AdventureExecutionResult(); //default: isSuccess=false

    }
}
