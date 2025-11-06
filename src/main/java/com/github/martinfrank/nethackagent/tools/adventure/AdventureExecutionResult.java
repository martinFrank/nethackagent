package com.github.martinfrank.nethackagent.tools.adventure;

import com.github.martinfrank.nethackagent.tools.inventory.Item;

import java.util.List;

public class AdventureExecutionResult {
    private long deltaMoney;
    private long deltaHealth;

    private List<Item> addedItems;
    private List<Item> removedItems;

    private AdventureInfo adventure;

    private boolean wasSuccess = false;

    private long deltaAdventures;

    public long getDeltaMoney() {
        return deltaMoney;
    }

    public void setDeltaMoney(long deltaMoney) {
        this.deltaMoney = deltaMoney;
    }

    public long getDeltaHealth() {
        return deltaHealth;
    }

    public void setDeltaHealth(long deltaHealth) {
        this.deltaHealth = deltaHealth;
    }

    public List<Item> getAddedItems() {
        return addedItems;
    }

    public void setAddedItems(List<Item> addedItems) {
        this.addedItems = addedItems;
    }

    public List<Item> getRemovedItems() {
        return removedItems;
    }

    public void setRemovedItems(List<Item> removedItems) {
        this.removedItems = removedItems;
    }

    public AdventureInfo getAdventure() {
        return adventure;
    }

    public void setAdventure(AdventureInfo adventure) {
        this.adventure = adventure;
    }

    public boolean isWasSuccess() {
        return wasSuccess;
    }

    public void setWasSuccess(boolean wasSuccess) {
        this.wasSuccess = wasSuccess;
    }

    public long getDeltaAdventures() {
        return deltaAdventures;
    }

    public void setDeltaAdventures(long deltaAdventures) {
        this.deltaAdventures = deltaAdventures;
    }

    @Override
    public String toString() {
        return "AdventureExecutionResult{" +
                "deltaMoney=" + deltaMoney +
                ", deltaHealth=" + deltaHealth +
                ", addedItems=" + addedItems +
                ", removedItems=" + removedItems +
                ", adventure=" + adventure +
                ", wasSuccess=" + wasSuccess +
                ", deltaAdventures=" + deltaAdventures +
                '}';
    }
}
