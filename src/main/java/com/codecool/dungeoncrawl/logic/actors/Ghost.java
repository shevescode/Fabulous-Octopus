package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.concurrent.ThreadLocalRandom;

public class Ghost extends Actor {
    private int attack = 10;

    public Ghost(Cell cell) {
        super(cell);
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = this.getNextCell();
        if(nextCell != null) {
            if(nextCell.getActor() == null) {
                moveToNextCell(nextCell);
            } else if (nextCell.getActor() != null) {
                //pass
            }
        }
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public void attackMonster(Actor actor) {
    }

    @Override
    public int getAttack() {
        return attack;
    }
}
