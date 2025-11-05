package com.github.martinfrank.nethackagent.tools.inventory;

import com.github.martinfrank.nethackagent.tools.LoginManager;
import dev.langchain4j.agent.tool.Tool;
import net.sourceforge.kolmafia.AdventureResult;
import net.sourceforge.kolmafia.equipment.Slot;
import net.sourceforge.kolmafia.persistence.ItemDatabase;
import net.sourceforge.kolmafia.session.EquipmentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EquipmentTool {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentTool.class);

    @Tool(
            name = "EquipmentListTool"
            , value = """
            Mit diesem Tool kann man eine Liste aller Gegenstände des Spielers
            ausgeben, die er gerade trägt. Mit diesem Tool kannst du überprüfen,
            wie die aktuelle Ausrüstung des Spielers ist.
            """
    )
    public List<Item> getEquipment() {
        logger.debug("using equipment tool");
        LoginManager.ensureLogin();

        Map<Integer, String> rawItems = ItemDatabase.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<Item> items = new ArrayList<>();
        for(Map.Entry<Slot, AdventureResult> entry: EquipmentManager.allEquipment().entrySet()){
            int itemId = entry.getValue().getItemId();
            if(itemId >= 0) {
                String name = rawItems.get(itemId);
                List<String> attributes = ItemDatabase.getAttributes(itemId).stream().map(a -> a.description).toList();
                Item item = new Item();
                item.setAttributes(attributes);
                item.setCount(1);
                item.setName(name);
                item.setId(itemId);
                item.setSlot(entry.getKey().name());
                items.add(item);
            }
        }
        items.forEach(item -> logger.debug(" - {}", item));

        return items;
    }

}
