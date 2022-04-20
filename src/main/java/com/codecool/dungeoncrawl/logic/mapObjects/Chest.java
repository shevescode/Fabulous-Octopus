package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Chest extends MapObject implements Lootable {
    private boolean open;
    private List<Item> lootItems;

    public Chest(Cell cell) {
        super(cell);
        open = false;
        lootItems=drawItem();
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

    @Override
    public List<Item> getLootItems() {
        return lootItems;
    }

    @Override
    public boolean isNotEmpty() {
        return lootItems != null;
    }

    @Override
    public void removeItem(int i) {
        lootItems.remove(i);
    }
}
