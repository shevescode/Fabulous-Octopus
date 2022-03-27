package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    private int attack = 2;

    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public void attackMonster(Actor actor) {
    }

    @Override
    public int getAttack() {
        return attack;
    }
}
