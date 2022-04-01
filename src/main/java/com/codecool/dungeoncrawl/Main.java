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

import static com.sun.javafx.application.PlatformImpl.exit;

public class Main extends Application {
    GameMap map = MapLoader.loadMap("/map.txt");
    int gameLevel = 0;
    Player player = new Player(map.getFirstPlayerCell());

    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            20 * Tiles.TILE_WIDTH);
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
                decrementYOffset();
                refresh();
            }
            case DOWN -> {
                map.getPlayer().move(0, 1);
                incrementYOffset();
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

    private void incrementYOffset() {
        if (yOffset + 20 < map.getHeight()) {
            if (player.getY() > 13 + yOffset) {
                yOffset += 1;
            }
        }
    }

    private void decrementYOffset() {
        if (yOffset - 1 >= 0) {
            if (player.getY() - yOffset < 6) {
                yOffset -= 1;
            }
        }
    }

    private int yOffset = 0;

    private void refresh() {
        System.out.println("y offset: " + yOffset);
        monstersMove();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        rightUI.hideButton();

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = getYStart(yOffset); y < getYEnd(yOffset); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    if (cell.getActor().getTileName().equals("player") && cell.getItem() != null) {
                        rightUI.showPickButton();
                        rightUI.buttonOnClick(cell);
                    }
                    Tiles.drawTile(context, cell.getActor(), x, y - yOffset);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y - yOffset);
                } else {
                    Tiles.drawTile(context, cell, x, y - yOffset);
                }
            }
        }

        rightUI.setHealthLabel();
        rightUI.setAttackLabel();
        if (playerGoDownstairs()) {
            changeMap(1);
        } else if (playerGoUpstairs()) {
            changeMap(-1);
        }
    }

    private int getYStart(int yOffset) {
        return Math.max(yOffset - 20, 0);
    }

    private int getYEnd(int yOffset) {
        return Math.min(yOffset + 20, map.getHeight());
    }


    private GameMap map1 = null;
    private GameMap map2 = null;

    private void changeMap(int i) {
        gameLevel += i;
        System.out.println(gameLevel);
        switch (gameLevel) {
            case 0 -> {
                map2 = map;
                map2.getPlayer().getCell().setActor(null);
                map2.setPlayer(null);
                map = map1;
                player.setCell(map.getCell(20, 15));
                map.getCell(20, 15).setActor(player);
                map.setPlayer(player);
            }
            case 1 -> {
                map1 = map;
                map1.getPlayer().getCell().setActor(null);
                map1.setPlayer(null);

                if (map2 == null) {
                    map2 = MapLoader.loadMap("/level2.txt");
                }
                map = map2;
                player.setCell(map.getFirstPlayerCell());
                map.getFirstPlayerCell().setActor(player);
                map.setPlayer(player);
            }
            default -> {

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
