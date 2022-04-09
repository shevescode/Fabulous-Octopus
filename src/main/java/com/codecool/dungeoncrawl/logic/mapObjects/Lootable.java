package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.items.HealthPotion;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.Random;

public interface Lootable {

    default Item drawItem() {
        int i = new Random().nextInt(0, 2);
        switch (i) {
            case 0 -> {
                return new HealthPotion();
            }
            case 1 -> {
                return new Sword();
            }
        }
        return null;
    }
}
