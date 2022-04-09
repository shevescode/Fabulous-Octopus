package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.List;


public class Player extends Actor {
    private List<Item> inventory;

    public Player(Cell cell) {
        super(cell);
        setHealth(30);
        setAttack(5);
    }

    public void playerMakeMove(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        if (getCell().isNextCellOnMap(nextCell)) {
            switch (nextCell.getType()) {
                case CLOSED_DOOR -> {
                    if (hasDoorKey()) {
                        openDoor(nextCell);
                    }
                }
                case CHEST -> {
                    if (nextCell.getChest().isOpen()) {
                        moveToNextCell(nextCell);
                    } else if (hasChestKey()) {
                        openChest(nextCell);
                    }
                }
                case OPEN_DOOR, FLOOR, STAIRS_DOWN, STAIRS_UP -> {
                    if (nextCell.isActorOnCell()) {
                        this.combat(nextCell.getActor());
                    } else {
                        moveToNextCell(nextCell);
                    }
                }
            }
        }
    }

    public String getTileName() {
        return "player";
    }

    public void pickUpItem(Item item) {
        if (item instanceof Sword) {
//            setAttack(getAttack() + ((Sword) sitem).getDamage());
            addAttackPoints(item);
        }
        inventory.add(item);
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    private boolean hasDoorKey() {
        return inventory.stream().anyMatch(Item -> (Item instanceof DoorKey));
    }

    private boolean hasChestKey() {
        return inventory.stream().anyMatch(Item -> (Item instanceof ChestKey));
    }

    private void openDoor(Cell cell) {
        removeKey(DoorKey.class);
        cell.setType(CellType.OPEN_DOOR);
    }

    private void openChest(Cell cell) {
        removeKey(ChestKey.class);
        cell.getChest().openChest();
        Item chestItem= cell.getChest().drawItem(); //todo getter z Chest

        if (chestItem instanceof Sword) {
            addAttackPoints(chestItem);
            inventory.add(chestItem);
        } else if (chestItem instanceof HealthPotion) {
            addHealthPoints(chestItem);
        } //todo przeniesc gdzies indziej
    }

    public void removeKey(Class<?> keyType) {
        Item key = inventory.stream()
                .filter(keyType::isInstance)
                .findAny()
                .orElse(null);
        inventory.remove(key);
    }

    private void addAttackPoints(Item chestItem) {
        setAttack(getAttack() + ((Sword) chestItem).getDamage());
    }

    private void addHealthPoints(Item chestItem) {
        setHealth(getHealth() + ((HealthPotion) chestItem).getHealth());
    }

}
