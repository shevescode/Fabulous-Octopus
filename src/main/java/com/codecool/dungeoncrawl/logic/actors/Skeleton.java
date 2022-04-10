package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor implements Monster {

    public Skeleton(Cell cell) {
        super(cell);
        setHealth(7);
        setAttack(2);
    }

    @Override
    public void monsterMakeMove() {
        Cell nextCell = this.getNextCellForMonsterMove();
        if (getCell().isNextCellOnMap(nextCell)) {
            switch (nextCell.getType()) {
                case OPEN_DOOR, FLOOR -> {
                    if (nextCell.isActorOnCell()) {
                        if(nextCell.getActor() instanceof Player) {
//                            this.combat(nextCell.getActor());
                        }
                    } else {
                        moveToNextCell(nextCell);
                    }
                }
            }
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
