package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.List;

public class Chest extends MapObject implements Lootable {
    private boolean open;
    private Item item;
    private List<Item> itemInChest;

    public Chest(Cell cell) {
        super(cell);
        open = false;
        itemInChest = drawItem();
        System.out.println(itemInChest.size());

    }

    public String getTileName() {
        if (open) {
            return "openChest";
        } else {
            return "closedChest";
        }

    }

    public List<Item> getItemInChest() {
        return itemInChest;
    }

    public boolean isOpen() {
        return open;
    }

    public void openChest() {
        open = true;
    }

    public Item getItem() {
        return item;
    }

    public void removeItem() {
        this.item = null;
    }

    public boolean isNotEmpty() {
        return itemInChest != null;
    }
}
