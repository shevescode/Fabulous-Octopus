package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class Spider extends Actor implements Monster{

    public Spider(Cell cell) {
        super(cell);
        setHealth(1);
        setAttack(1);
    }

    @Override
    public String getTileName() {
        return "spider";
    }

    @Override
    public void monsterMakeMove() {
        Cell nextCell = this.getNextCellForMonsterMove();
        Cell currentCell = getCell();
        if (currentCell.isNextCellOnMap(nextCell)) {
            switch (nextCell.getType()) {
                case OPEN_DOOR, FLOOR, WALL -> {
                    if (nextCell.isActorOnCell()) {
                        if(nextCell.getActor() instanceof Player) {
//                            this.combat(nextCell.getActor());
                        }
                    } else {
                        moveToNextCell(nextCell);
                        duplicate(currentCell);
                    }
                }
            }
        }
    }
    
    public void duplicate(Cell cell) {
        int drawDice = new Random().nextInt(1,25);
        if (drawDice==1) {
            new Spider(cell);
        }
    }
}
