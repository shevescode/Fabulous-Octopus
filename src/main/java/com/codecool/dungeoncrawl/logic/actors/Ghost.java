package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Actor implements Monster{
    private int attack = 10;

    public Ghost(Cell cell) {
        super(cell);
        setHealth(10);
    }


    public void monsterMakeMove() {
        Cell nextCell = this.getNextCellForMonsterMove();
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
    public int getAttack() {
        return attack;
    }
}
