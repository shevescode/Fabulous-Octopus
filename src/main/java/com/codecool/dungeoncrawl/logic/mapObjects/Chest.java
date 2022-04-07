package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.HealthPotion;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.Random;

public class Chest implements Drawable {
    private Item item;
    private Cell cell;
    private boolean open;

    public Chest(Cell cell) {
        this.cell = cell;
        open = false;
        item = drawItem();
    }

    public String getTileName() {
        if(open) {
            return "openChest";
        } else {
            return "closedChest";
        }

    }

    public boolean isOpen() {
        return open;
    }

    public void openChest() {
        open = true;
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
