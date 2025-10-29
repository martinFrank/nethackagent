package com.github.martinfrank.nethackagent.tools.inventory;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private int id;
    private String name;

    private int price;
    private List<String> attributes;
    private int count;
    private String slot;

    public Item() {
    }

    public Item(Item item) {
        this.id = item.id;
        this.name = item.name;
        this.price = item.price;
        this.attributes = new ArrayList<>(item.attributes);
        this.count = item.count;
        this.slot = item.slot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }



    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", attributes=" + attributes +
                ", count=" + count +
                ", slot=" + slot +
                '}';
    }
}
