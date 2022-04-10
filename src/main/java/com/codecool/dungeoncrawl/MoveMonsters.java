package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Monster;

public class MoveMonsters implements Runnable {
    private GameMap map;
    private Main main;

    public MoveMonsters(GameMap map, Main main) {
        this.map = map;
        this.main = main;
    }


    @Override
    public void run() {
        synchronized (GameMap.class) {
            for (int i = 0; i < 100_000; i++) {
                try {
                    Thread.sleep(1000);
                    for (Monster monster : map.getAllMonsters()) {
                        monster.monsterMakeMove();
                        main.refresh();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void setMap(GameMap map) {
        this.map = map;
    }
}
