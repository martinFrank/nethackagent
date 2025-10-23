package com.github.martinfrank.nethackagent.tools.inventory;

import com.github.martinfrank.nethackagent.LoginManager;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.persistence.ItemDatabase;
import net.sourceforge.kolmafia.session.InventoryManager;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryListTool {

    @Tool(
            name = "InventoryListTool"
            , value = """
            Mit diesem Tool kann man eine Liste aller Gegenstände im Inventory des
            Spielers ausgeben. Immer wenn du wissen willst, was für Gegenstände
            der Spieler besitzt, kannst du diese Tool verwenden
            """
    )
    public List<Item> getInventory() {
        LoginManager.ensureLogin();

        Map<Integer, String> rawItems = ItemDatabase.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<Item> items = new ArrayList<>();
        for(Integer itemId: rawItems.keySet()) {
            int count = InventoryManager.getCount(itemId);
            if (count > 0) {
                List<String> attributes = ItemDatabase.getAttributes(itemId).stream().map(a -> a.description).toList();

                Item item = new Item();
                item.setAttributes(attributes);
                item.setCount(count);
                item.setName(rawItems.get(itemId));
                item.setId(itemId);
                items.add(item);
                item.setSlot("INVENTORY");
            }
        }

        return items;
    }

}