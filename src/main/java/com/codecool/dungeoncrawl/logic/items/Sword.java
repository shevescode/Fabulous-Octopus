package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Sword extends Item{
    private final int damage = 2;
    public Sword(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "sword";
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "Sword - " + damage + " attack";
    }
}
