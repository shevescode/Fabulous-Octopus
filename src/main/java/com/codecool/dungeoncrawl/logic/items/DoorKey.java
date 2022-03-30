package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class DoorKey extends Item {

    public DoorKey(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "door key";
    }

    @Override
    public String toString() {
        return "Door key ";
    }
}
