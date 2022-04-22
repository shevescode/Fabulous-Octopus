package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class ChestKey extends Item {

    public ChestKey(Cell cell) {
        super(cell);
    }

    public ChestKey() {

    }

    public String getTileName() {
        return "chest key";
    }

    @Override
    public String toString() {
        return "Chest key";
    }
}
