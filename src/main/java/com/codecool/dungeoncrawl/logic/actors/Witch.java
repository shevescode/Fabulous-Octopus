package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.concurrent.ThreadLocalRandom;

public class Witch extends Actor implements Monster {
    private int attack = 9;
    private int mana = 0;

    public Witch(Cell cell) {
        super(cell);
        setHealth(1);
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public String getTileName() {
        return "witch";
    }

    @Override
    public void monsterMakeMove() {
        mana += 1;
        if(mana == 10) {
            GameMap map = this.getCell().getGameMap();
            int randomX = ThreadLocalRandom.current().nextInt(0, map.getWidth() + 1);
            int randomY = ThreadLocalRandom.current().nextInt(0, map.getHeight() + 1);
            Cell nextCell = map.getCell(randomX, randomY);
            if(nextCell != null) {
                if(nextCell.getType() != CellType.WALL && nextCell.getActor() == null) {
                    moveToNextCell(nextCell);
                }
            }
            mana = 0;
        }
    }
}
