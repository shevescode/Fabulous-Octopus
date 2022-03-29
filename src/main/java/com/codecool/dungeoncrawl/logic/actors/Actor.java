package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void subtractHealthPoints(int i) {
        health -= i;
    }

    public abstract int getAttack();

    public Cell getNextCellForMonsterMove() {
        Cell nextCell = this.getCell().getNeighbor(0, 0);
        int randomDirection = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        switch (randomDirection) {
            case 1 -> {
                nextCell = this.getCell().getNeighbor(0, -1);
            }
            case 2 -> {
                nextCell = this.getCell().getNeighbor(0, 1);
            }
            case 3 -> {
                nextCell = this.getCell().getNeighbor(-1, 0);
            }
            case 4 -> {
                nextCell = this.getCell().getNeighbor(1, 0);
            }
        }
        return nextCell;
    }

    void moveToNextCell(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
