package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.mapObjects.Chest;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private List<GameMap> savedMaps;
    private GameMap map;
    private GameMap mapLevelZeroSave;
    private GameMap mapLevelOneSave;
    private MoveMonsters monsterMove;
    private Player player;
    private int gameLevel = 0;

    private RightUI rightUI;

    private Canvas canvas;
    private GraphicsContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(
                25 * Tiles.TILE_WIDTH,
                20 * Tiles.TILE_WIDTH);
        context = canvas.getGraphicsContext2D();

        map = MapLoader.loadMap("/map.txt");
        player = new Player(map.getFirstPlayerCell());
        map.setPlayer(player);
        rightUI = new RightUI(map.getPlayer());
        map.getPlayer().setInventory(rightUI.getInventory().getItems());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(rightUI);

        savedMaps = new ArrayList<>();
        savedMaps.add(mapLevelZeroSave);
        savedMaps.add(mapLevelOneSave);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        rightUI.setHealthLabel();
        rightUI.setAttackLabel();
        rightUI.hideButton();
        rightUI.clearChestLootGrid();

        primaryStage.setTitle("Fabulous Octopus");
        primaryStage.show();
        scene.setOnKeyPressed(this::onKeyPressed);



//        Runnable r = () -> {
//            for (int i = 0; i < 100_000; i++) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                monstersMove();
//                refresh();
//            }
//        };
        monsterMove = new MoveMonsters(map, this);
        Thread thread = new Thread(monsterMove);
        thread.start();

    }

    private void onKeyPressed(KeyEvent keyEvent) {

        rightUI.setHealthLabel();
        rightUI.setAttackLabel();
        rightUI.hideButton();
        rightUI.clearChestLootGrid();

        switch (keyEvent.getCode()) {
            case UP, W -> {
                map.getPlayer().playerMakeMove(0, -1);
                map.decrementYOffset();
                refresh();
            }
            case S, DOWN -> {
                map.getPlayer().playerMakeMove(0, 1);
                map.incrementYOffset();
                refresh();
            }
            case A, LEFT -> {
                map.getPlayer().playerMakeMove(-1, 0);
                map.decrementXOffset();
                refresh();

            }
            case D, RIGHT -> {
                map.getPlayer().playerMakeMove(1, 0);
                map.incrementXOffset();
                refresh();
            }
        }
        if (map.getPlayer().getCell().isItemOnCell()) {
            rightUI.showPickButton();
            rightUI.buttonOnClick(map.getPlayer().getCell());
        } else if (isPlayerStandingOnChest() && map.getPlayer().getCell().isMapObjectOnCell() && ((Chest)map.getPlayer().getCell().getMapObject()).getItem() != null) {
            rightUI.checkChestLoot(map.getPlayer().getCell());
            rightUI.showPickButton();
            rightUI.buttonOnClick(map.getPlayer().getCell());
        }
    }

    public void refresh() {
//        monstersMove();

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


        for (int x = getXStart(); x < getXEnd(); x++) {
            for (int y = getYStart(); y < getYEnd(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.isActorOnCell()) {
                    Tiles.drawTile(context, cell.getActor(), x - map.getXOffset(), y - map.getYOffset());
                } else if (cell.isItemOnCell()) {
                    Tiles.drawTile(context, cell.getItem(), x - map.getXOffset(), y - map.getYOffset());
                } else if (cell.isMapObjectOnCell()) {
                    Tiles.drawTile(context, cell.getMapObject(), x - map.getXOffset(), y - map.getYOffset());
                } else {
                    Tiles.drawTile(context, cell, x - map.getXOffset(), y - map.getYOffset());
                }
            }
        }

//
        if (isPlayerGoingDownstairs()) {
            changeMap(1);
        } else if (isPlayerGoingUpstairs()) {
            changeMap(-1);
        }
    }

    private int getYStart() {
        return Math.max(map.getYOffset() - 20, 0);
    }

    private int getYEnd() {
        return Math.min(map.getYOffset() + 20, map.getHeight());
    }

    private int getXStart() {
        return Math.max(map.getXOffset() - 25, 0);
    }

    private int getXEnd() {
        return Math.min(map.getXOffset() + 25, map.getWidth());
    }


    private void changeMap(int i) {
        gameLevel += i;
        switch (gameLevel) {
            case 0 -> {
                mapLevelOneSave = map;
                mapLevelOneSave.getPlayer().getCell().setActor(null);
                mapLevelOneSave.setPlayer(null);
                map = mapLevelZeroSave;
                monsterMove.setMap(map);
                player.setCell(map.getCell(20, 15));
                map.getCell(20, 15).setActor(player);
                map.setPlayer(player);
            }
            case 1 -> {
                mapLevelZeroSave = map;
                mapLevelZeroSave.getPlayer().getCell().setActor(null);
                mapLevelZeroSave.setPlayer(null);

                if (mapLevelOneSave == null) {
                    mapLevelOneSave = MapLoader.loadMap("/level2.txt");
                }
                map = mapLevelOneSave;
                monsterMove.setMap(map);
                player.setCell(map.getFirstPlayerCell());
                map.getFirstPlayerCell().setActor(player);
                map.setPlayer(player);
            }
        }

        refresh();
    }

    private boolean isPlayerGoingDownstairs() {
        return map.getPlayer().getCell().getType() == CellType.STAIRS_DOWN;
    }

    private boolean isPlayerGoingUpstairs() {
        return map.getPlayer().getCell().getType() == CellType.STAIRS_UP;
    }

    private boolean isPlayerStandingOnChest() {
        return map.getPlayer().getCell().getType() == CellType.CHEST;
    }

    private void monstersMove() {
        for (Monster monster : map.getAllMonsters()) {
            monster.monsterMakeMove();
        }
    }
}
