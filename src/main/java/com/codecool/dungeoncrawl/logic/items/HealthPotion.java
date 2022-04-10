package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class HealthPotion extends Item {
    private final int health = 2;
//    public HealthPotion(Cell cell) {
//        super(cell);
//    }

    public HealthPotion() {
        super();

    }

    public String getTileName() {
        return "health potion";
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {
        return "Health potion";
    }
}
