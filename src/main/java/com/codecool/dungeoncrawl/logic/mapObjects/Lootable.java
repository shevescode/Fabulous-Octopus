package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.items.Hammer;
import com.codecool.dungeoncrawl.logic.items.HealthPotion;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Sword;

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
        System.out.println(numberOfItems);
        for (int x = 0; x < numberOfItems; x++) {
            int randomInt = new Random().nextInt(0, 3);
            switch (randomInt) {
                case 0 -> {
                    lootItems.add(new HealthPotion());
                }
                case 1 -> {
                    lootItems.add(new Sword());
                }
                case 2 -> {
                    lootItems.add(new Hammer());
                }
            }
        }
        return lootItems;
    }
}
