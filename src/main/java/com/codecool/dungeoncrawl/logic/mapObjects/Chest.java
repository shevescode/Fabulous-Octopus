package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Chest extends MapObject implements Lootable {
    private boolean open;
    private Item item;

    public Chest(Cell cell) {
        super(cell);
        open = false;
        item = drawItem();

    }

    public String getTileName() {
        if (open) {
            return "openChest";
        } else {
            return "closedChest";
        }

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
        return item != null;
    }
}
