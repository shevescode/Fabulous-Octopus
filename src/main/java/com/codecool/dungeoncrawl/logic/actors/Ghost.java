package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Actor {
    private int attack = 10;

    public Ghost(Cell cell) {
        super(cell);
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
