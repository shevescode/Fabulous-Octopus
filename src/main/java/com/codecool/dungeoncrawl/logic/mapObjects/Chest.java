package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.List;

public class Chest extends MapObject implements Lootable {
    private boolean open;
    private Item item;
    private List<Item> itemsInChest;

    public Chest(Cell cell) {
        super(cell);
        open = false;
        itemsInChest = drawItem();


    }

    public String getTileName() {
        if (open) {
            return "openChest";
        } else {
            return "closedChest";
        }

    }

    public List<Item> getItemsInChest() {
        return itemsInChest;
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

    public void removeItem(int i) {
        itemsInChest.remove(i);
    }

    public boolean isNotEmpty() {
        return itemsInChest != null;
    }
}
