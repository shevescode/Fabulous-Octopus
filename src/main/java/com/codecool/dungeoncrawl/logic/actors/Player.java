package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {
    private List<Item> inventory;
    private int attack = 5;

    public Player(Cell cell) {
        super(cell);
        setHealth(20);
    }

    public void move(int dx, int dy) {
        Cell nextCell = getCell().getNeighbor(dx, dy);
        if (nextCell != null) {
            if ((nextCell.getType() == CellType.FLOOR && nextCell.getActor() == null) || nextCell.getType() == CellType.OPEN_DOOR || nextCell.getType() == CellType.STAIRS_DOWN || nextCell.getType() == CellType.STAIRS_UP) {
                moveToNextCell(nextCell);
            } else if (nextCell.getActor() instanceof Monster) {
                attackMonster(nextCell.getActor());

                if (nextCell.getActor().getHealth() <= 0) {
                    nextCell.removeDeadMonster();
                    moveToNextCell(nextCell);
                }
            } else if (nextCell.getType() == CellType.CLOSED_DOOR && hasKey()) {
                openDoor(nextCell);
                moveToNextCell(nextCell);
            }
        }
    }

    public String getTileName() {
        return "player";
    }

    public void pickUpItem(Item item) {
        if (item instanceof Sword) {
            attack += ((Sword) item).getDamage();
        }
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    private void attackMonster(Actor actor) {
        actor.subtractHealthPoints(attack);
        this.subtractHealthPoints(actor.getAttack());
    }

    private boolean hasKey() {
        return inventory.stream().anyMatch(Item -> Item instanceof Key);
    }

    private void openDoor(Cell cell) {
        cell.setType(CellType.OPEN_DOOR);
        Item key = inventory.stream()
                .filter(Item -> Item instanceof Key)
                .findFirst()
                .get();
        System.out.println(key);
        inventory.remove(key);
    }


}
