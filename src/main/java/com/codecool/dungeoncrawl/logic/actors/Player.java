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
                case CLOSED_CHEST -> {
                    if (hasChestKey()) {
                        openChest(nextCell);
                    }
                }
                case OPEN_DOOR, OPEN_CHEST, FLOOR, STAIRS_DOWN, STAIRS_UP -> {
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
            setAttack(getAttack() + ((Sword) item).getDamage());
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
        removeKey(CellType.CLOSED_DOOR);
        cell.setType(CellType.OPEN_DOOR);
    }

    private void openChest(Cell cell) {
        removeKey(CellType.CLOSED_CHEST);
        cell.setType(CellType.OPEN_CHEST);
    }

    private void removeKey(CellType cellType) {
        switch (cellType) {
            case CLOSED_DOOR -> {
                Item key = inventory.stream()
                        .filter(Item -> Item instanceof DoorKey)
                        .findAny()
                        .get();
                inventory.remove(key);
            }
            case CLOSED_CHEST -> {
                Item key = inventory.stream()
                        .filter(Item -> Item instanceof ChestKey)
                        .findAny()
                        .get();
                inventory.remove(key);
            }
        }

    }


//    private void obtainObject() {
// random integer and switch statement for chosing item -> health potion, sword, hammer, łuk XDDD
//    }

}
