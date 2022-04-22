package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.mapObjects.Lootable;
import com.codecool.dungeoncrawl.logic.mapObjects.MapObject;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Double.MAX_VALUE;
import static javafx.application.Platform.exit;

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
    private BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_RGB);


    private enum STATE {
        MENU,
        GAME
    }

    private Menu menu;


    private STATE State = STATE.MENU;


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
        rightUI.getInventory().setPlayer(player);
        player.setRightUI(rightUI);

        map.getPlayer().setInventory(rightUI.getInventory().getItems());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(rightUI);

        menu = new Menu();
        primaryStage.setTitle("Fabulous Octopus");
        VBox vbox = new VBox();
        vbox.setBackground(new Background(new BackgroundFill(Color.valueOf("#472D3C"), CornerRadii.EMPTY, Insets.EMPTY)));

        savedMaps = new ArrayList<>();
        savedMaps.add(mapLevelZeroSave);
        savedMaps.add(mapLevelOneSave);
        System.out.println(State);
        Scene sceneGame = new Scene(borderPane);
        if (State == STATE.GAME) {

            primaryStage.setScene(sceneGame);
            refresh();
            rightUI.hideButton();

            primaryStage.show();
            sceneGame.setOnKeyPressed(this::onKeyPressed);
        } else {
            vbox.setPrefWidth(90);
            vbox.setPrefHeight(0);
            for(int i=0; i<4;i++) {
                Button startButton = new Button("START GAME");
            }
            Button startButton = new Button("START GAME");
            Button optionButton = new Button("OPTIONS");
            Button exitButton = new Button("EXIT GAME");

            startButton.setPrefWidth(MAX_VALUE);
            optionButton.setPrefWidth(MAX_VALUE);
            exitButton.setPrefWidth(MAX_VALUE);
            startButton.setPrefHeight(MAX_VALUE);
            optionButton.setPrefHeight(MAX_VALUE);
            exitButton.setPrefHeight(MAX_VALUE);

            vbox.getChildren().addAll(startButton, optionButton, exitButton);

            Scene scene = new Scene(vbox);
            primaryStage.setScene(scene);
            primaryStage.show();

            startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    System.out.println("kliknięty");
                    System.out.println(State);
                   State = STATE.GAME;
                    start(primaryStage);
                }
            });
            exitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                  exit();
                }
            });



        }


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

        rightUI.hideButton();
        rightUI.clearLootGrids();

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
        } else if (map.getPlayer().getCell().isMapObjectOnCell()) {
            rightUI.drawLoot(map.getPlayer().getCell());
            rightUI.addGridEvent(map.getPlayer().getCell());
        }
    }

    public void refresh() {
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
                    Tiles.drawTile(context, cell.getMapObjects().get(0), x - map.getXOffset(), y - map.getYOffset());
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
