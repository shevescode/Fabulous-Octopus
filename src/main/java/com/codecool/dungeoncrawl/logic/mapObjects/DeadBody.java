package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.List;

public class DeadBody extends MapObject implements Lootable {

    private List<Item> lootItems;

    public DeadBody(Cell cell) {
        super(cell);
        lootItems = drawItem();
    }

    @Override
    public String getTileName() {
        return "deadBody";
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
