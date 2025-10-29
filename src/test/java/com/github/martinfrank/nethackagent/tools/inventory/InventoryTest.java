package com.github.martinfrank.nethackagent.tools.inventory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void testAdded(){
        Item a = createItem("A", 1, 20);
        Item b = createItem("B", 2, 20);
        Inventory before = new Inventory(List.of(a,b));

        Item a2 = createItem("A", 1, 22);
        Item c = createItem("C", 3, 20);
        Inventory after = new Inventory(List.of(a2,c));

        List<Item> added = before.findAdded(after);
        Assertions.assertEquals(2, added.getFirst().getCount());
        Assertions.assertEquals("C", added.getLast().getName());
    }

    @Test
    void testRemoved(){
        Item a = createItem("A", 1, 20);
        Item b = createItem("B", 2, 20);
        Inventory before = new Inventory(List.of(a,b));

        Item a2 = createItem("A", 1, 18);
        Item c = createItem("C", 3, 20);
        Inventory after = new Inventory(List.of(a2,c));

        List<Item> removed = before.findRemoved(after);
        Assertions.assertEquals(2, removed.getFirst().getCount());
        Assertions.assertEquals("B", removed.getLast().getName());
    }

    private Item createItem(String name, int id, int amount) {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setCount(amount);
        item.setAttributes(Collections.emptyList());
        return item;
    }
}