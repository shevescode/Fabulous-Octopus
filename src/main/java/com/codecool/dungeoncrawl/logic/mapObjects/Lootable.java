package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Lootable {


    List<Item> getLootItems();

    boolean isNotEmpty();

    void removeItem(int i);


    default List<Item> drawItem() {
        List<Item> lootItems = new ArrayList<>();
        int numberOfItems = new Random().nextInt(0, 3);
        for (int x = 0; x < numberOfItems; x++) {
            int randomInt = new Random().nextInt(0, 6);
            switch (randomInt) {
                case 0,4 -> {
                    lootItems.add(new HealthPotion());
                }
                case 1,5-> {
                    lootItems.add(new Sword());
                }
                case 2 -> {
                    lootItems.add(new Hammer());
                }
                case 3 -> {
                    lootItems.add(new ChestKey());
                }
            }
        }
        return lootItems;
    }
}
