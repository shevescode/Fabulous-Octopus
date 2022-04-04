package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.Random;

public class Witch extends Actor implements Monster {
    private int mana = 0;

    public Witch(Cell cell) {
        super(cell);
        setHealth(1);
        setAttack(20);
    }

    @Override
    public void monsterMakeMove() {
        mana += 1;
        if (mana == 10) {
            GameMap map = this.getCell().getGameMap();
            int randomY = new Random().nextInt(0, map.getHeight());
            int randomX = new Random().nextInt(0, map.getWidth());
            Cell nextCell = map.getCell(randomX, randomY);
            if (getCell().isNextCellOnMap(nextCell)) {
                switch (nextCell.getType()) {
                    case FLOOR, EMPTY -> {
                        if (nextCell.isActorOnCell()) {
                            if(nextCell.getActor() instanceof Player) {
                                this.combat(nextCell.getActor());
                            }
                        } else {
                            moveToNextCell(nextCell);
                        }
                    }
                }
            }
            mana = 0;
        }
    }

    @Override
    public String getTileName() {
        return "witch";
    }
}
