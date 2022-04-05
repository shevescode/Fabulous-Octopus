package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class Chest extends Item {
    private Item item;

    public Chest(Cell cell) {
        super(cell);
        item = drawItem();
    }

    public String getTileName() {
        return "chest";
    }

    private Item drawItem() {
        int i = new Random().nextInt(0, 2);
        System.out.println(i);
        switch (i) {
            case 0 -> {
                System.out.println(0);
                return new HealthPotion();
            }
            case 1 -> {
                System.out.println(1);
                return new Sword();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Magic chest";
    }
}
