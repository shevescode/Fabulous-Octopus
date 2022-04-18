package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.List;

public class DeadBody extends MapObject implements Lootable {
    private List<Item> itemsFromDeadBody;

    public DeadBody(Cell cell) {
        super(cell);
        itemsFromDeadBody = drawItem();
    }

    @Override
    public String getTileName() {
        return "deadBody";
    }

    public List<Item> getItemsFromDeadBody() {
        return itemsFromDeadBody;
    }

//    public Item getItem() {
//        return item;
//    }
}
