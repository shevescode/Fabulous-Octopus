package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.mapObjects.DeadBody;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int attack;

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

    public int getAttack() {
        return attack;
    }

    public void killActor() {
        health = 0;
        cell.setActor(null);
        cell.setMapObject(new DeadBody(cell));
        cell = null;

    }

    public Cell getNextCellForMonsterMove() {
        Cell nextCell = this.getCell().getNeighbor(0, 0);
        int randomDirection = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        switch (randomDirection) {
            case 1 -> nextCell = this.getCell().getNeighbor(0, -1);
            case 2 -> nextCell = this.getCell().getNeighbor(0, 1);
            case 3 -> nextCell = this.getCell().getNeighbor(-1, 0);
            case 4 -> nextCell = this.getCell().getNeighbor(1, 0);
        }
        return nextCell;
    }

    public void moveToNextCell(Cell nextCell) {
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

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void combat(Actor actor) {
        if (actor.canSurviveAttack(this.getAttack())) {
            actor.subtractHealthPoints(this.getAttack());
            if (this.canSurviveAttack(actor.getAttack())) {
                this.subtractHealthPoints(actor.getAttack());
            } else {
                this.killActor();
            }
        } else {
            actor.killActor();
        }
    }

    private boolean canSurviveAttack(int attack) {
        return health > attack;
    }

    private void subtractHealthPoints(int i) {
        if(i> 0) {
            health -= i;
        }
    }

}
