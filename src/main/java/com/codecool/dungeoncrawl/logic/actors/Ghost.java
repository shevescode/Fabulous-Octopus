package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Actor implements Monster {

    public Ghost(Cell cell) {
        super(cell);
        setHealth(10);
        setAttack(10);
    }



    public void monsterMakeMove() {
        Cell nextCell = this.getNextCellForMonsterMove();
        if (getCell().isNextCellOnMap(nextCell)) {
            if (nextCell.isActorOnCell()) {
                if(nextCell.getActor() instanceof Player) {
                    this.combat(nextCell.getActor());
                }
            } else {
                moveToNextCell(nextCell);
            }
        }
    }

    @Override
    public String getTileName() {
        return "ghost";
    }
}
