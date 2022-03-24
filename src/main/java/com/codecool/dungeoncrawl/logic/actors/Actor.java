package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int attack = 2;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if(nextCell != null) {
            if(nextCell.getType() != CellType.WALL && nextCell.getActor() == null) {
                moveToNextCell(nextCell);
            } else if (nextCell.getActor() != null) {
                this.attackMonster(nextCell.getActor());
                if(nextCell.getActor().getHealth() <= 0) {
                    nextCell.removeDeadMonster();
                    moveToNextCell(nextCell);
                }
            }
        }
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

    public void subtractHealthPoints(int i) {
        health -= i;
    }

    public abstract void attackMonster(Actor actor);

    private void moveToNextCell(Cell nextCell){
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

}
