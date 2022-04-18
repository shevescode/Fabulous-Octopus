package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Hammer extends Item {
    private final int damage = 5;

    public Hammer(Cell cell) {
        super(cell);
    }

    public Hammer() {

    }

    public String getTileName() {
        return "hammer";
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "Hammer - " + damage + " attack points";
    }
}
