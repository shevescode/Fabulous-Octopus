package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

public class DeadBody extends MapObject implements Lootable {
    private final Item item;

    public DeadBody(Cell cell) {
        super(cell);
        item = drawItem();
    }

    @Override
    public String getTileName() {
        return "deadBody";
    }

    public Item getItem() {
        return item;
    }
}
