package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    GameMap map = MapLoader.loadMap("/map.txt");
    int gameLevel = 0;
    Player player = new Player(map.getFirstPlayerCell());

    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    private RightUI rightUI;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        map.setPlayer(player);

        this.rightUI = new RightUI(map.getPlayer());

        map.getPlayer().setInventory(rightUI.getInventory().getItems());

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(rightUI);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);


        primaryStage.setTitle("Fabulous Octopus");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP -> {
                map.getPlayer().move(0, -1);
                refresh();
            }
            case DOWN -> {
                map.getPlayer().move(0, 1);
                refresh();
            }
            case LEFT -> {
                map.getPlayer().move(-1, 0);
                refresh();
            }
            case RIGHT -> {
                map.getPlayer().move(1, 0);
                refresh();
            }
        }
    }

    private void refresh() {
        monstersMove();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        rightUI.hideButton();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    if (cell.getActor().getTileName().equals("player") && cell.getItem() != null) {
                        rightUI.showPickButton();
                        rightUI.buttonOnClick(cell);
                    }
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }

        rightUI.setHealthLabel();
        rightUI.setAttackLabel();
        if(playerGoDownstairs()) {
            changeMap(1);
        } else if (playerGoUpstairs()) {
            changeMap(-1);
        }
    }

    private GameMap mapSave;
    private void changeMap(int i) {
        gameLevel += i;

        String level;
        switch(gameLevel) {
            case 0 -> {
                level = "/map.txt";
                map = mapSave;

            }
            case 1 -> {
                mapSave = map;
                mapSave.getPlayer().move(-1,0);
                level = "/level2.txt";
                map = MapLoader.loadMap(level);
                player.setCell(map.getFirstPlayerCell());
                map.getFirstPlayerCell().setActor(player);
                map.setPlayer(player);
            }
            default -> {
                level = "/easter.txt";
            }
        }

        refresh();
    }

    private boolean playerGoDownstairs() {
        return map.getPlayer().getCell().getType() == CellType.STAIRS_DOWN;
    }

    private boolean playerGoUpstairs() {
        return map.getPlayer().getCell().getType() == CellType.STAIRS_UP;
    }


    private void monstersMove() {
        for (Actor monster : map.getAllMonsters()) {
            ((Monster) monster).monsterMakeMove();
        }
    }


}
