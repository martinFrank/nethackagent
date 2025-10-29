package com.github.martinfrank.nethackagent.tools.inventory;

import com.github.martinfrank.nethackagent.LoginManager;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.objectpool.ItemPool;
import net.sourceforge.kolmafia.persistence.ItemDatabase;
import net.sourceforge.kolmafia.session.InventoryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryTool {

    private static final Logger logger = LoggerFactory.getLogger(InventoryTool.class);

    @Tool(
            name = "InventoryListTool"
            , value = """
            Mit diesem Tool kann man eine Liste aller Gegenstände im Inventory des
            Spielers ausgeben. Immer wenn du wissen willst, was für Gegenstände
            der Spieler besitzt, kannst du diese Tool verwenden
            """
    )
    public List<Item> getInventory() {
        logger.debug("using inventory tool");
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
                item.setPrice(ItemDatabase.getPriceById(itemId));
                item.setSlot("INVENTORY");
                items.add(item);
            }
        }

        items.forEach(item -> logger.debug(" - {}", item));

        return items;
    }

}
