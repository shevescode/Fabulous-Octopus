package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.logic.actors.Spider;
import javafx.application.Platform;

public class MoveMonsters implements Runnable {
    private GameMap map;
    private Game main;

    public MoveMonsters(GameMap map, Game main) {
        this.map = map;
        this.main = main;
    }


    @Override
    public void run() {
        synchronized (GameMap.class) {
            for (int i = 0; i < 100_000; i++) {
                try {
                    Thread.sleep(500);
                    for (Monster monster : map.getAllMonsters()) {
                        if(monster instanceof Spider) {
                            Platform.runLater(monster::monsterMakeMove);
                        }
                    }
                    Platform.runLater(() -> main.refresh());
                    Thread.sleep(500);
                    for (Monster monster : map.getAllMonsters()) {
                        Platform.runLater(monster::monsterMakeMove);
                    }
                    Platform.runLater(() -> main.refresh());
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
