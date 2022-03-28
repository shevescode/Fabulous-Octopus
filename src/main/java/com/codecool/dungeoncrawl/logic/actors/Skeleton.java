package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Skeleton extends Actor implements Monster {
    private int attack = 2;

    public Skeleton(Cell cell) {
        super(cell);
        setHealth(7);
    }
    public void monsterMakeMove() {
        Cell nextCell = this.getNextCellForMonsterMove();
        if(nextCell != null) {
        if(nextCell.getType() != CellType.WALL && nextCell.getActor() == null) {
                moveToNextCell(nextCell);
            } else if (nextCell.getActor() != null) {
                //pass
            }
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public int getAttack() {
        return attack;
    }
}
