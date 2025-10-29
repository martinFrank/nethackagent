package com.github.martinfrank.nethackagent.tools.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {

    private final List<Item> items;

    public Inventory(List<Item> items) {
        this.items = new ArrayList<>(items);
    }

    public Optional<Item> getById(int id){
        return items.stream().filter(i -> i.getId() == id).findAny();
    }

    public List<Item> findAdded(Inventory inventoryAfter) {
        List<Item> added = new ArrayList<>();
        for(Item item: inventoryAfter.items){
            Optional<Item> newItem = getById(item.getId());
            if(newItem.isPresent()){
                int diff = item.getCount() - newItem.get().getCount();
                if (diff > 0){
                    Item addedOne = new Item(item);
                    addedOne.setCount(diff);
                    added.add(addedOne);
                }
            }else{
                added.add(item);
            }
        }
        return added;
    }

    public List<Item> findRemoved(Inventory inventoryAfter) {
        List<Item> removed = new ArrayList<>();
        for(Item item: items){
            Optional<Item> removedItem = inventoryAfter.getById(item.getId());
            if(removedItem.isPresent()){
                int diff = item.getCount() - removedItem.get().getCount();
                if (diff > 0){
                    Item removedOne = new Item(item);
                    removedOne.setCount(diff);
                    removed.add(removedOne);
                }
            }
        }
        for(Item item: items){
            Optional<Item> existing = inventoryAfter.getById(item.getId());
            if(!existing.isPresent()){
                removed.add(item);
            }
        }
        return removed;
    }
}
