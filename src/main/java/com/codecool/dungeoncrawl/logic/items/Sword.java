package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Sword extends Item implements Weapon{
    private final int damage = 2;

    public Sword(Cell cell) {
        super(cell);
    }

    public Sword() {
    }

    public String getTileName() {
        return "sword";
    }


    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "Sword - " + damage + " attack points";
    }
}
