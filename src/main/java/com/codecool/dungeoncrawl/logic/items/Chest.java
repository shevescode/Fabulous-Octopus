package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Chest extends Item {

    public Chest(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "chest";
    }

    @Override
    public String toString() {
        return "Magic chest";
    }
}
