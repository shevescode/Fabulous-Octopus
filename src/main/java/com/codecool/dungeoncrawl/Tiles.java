package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;

        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("stairsUp", new Tile(2, 6));
        tileMap.put("stairsDown", new Tile(3, 6));
        tileMap.put("closedDoor", new Tile(0, 9));
        tileMap.put("openDoor", new Tile(2, 9));

        tileMap.put("player", new Tile(25, 8));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("ghost", new Tile(26, 6));
        tileMap.put("witch", new Tile(24, 1));
        tileMap.put("spider", new Tile(30, 5));

        tileMap.put("door key", new Tile(17, 23));
        tileMap.put("chest key", new Tile(16, 23));

        tileMap.put("sword", new Tile(0, 29));
        tileMap.put("health potion", new Tile(17, 25));
        tileMap.put("closedChest", new Tile(8, 6));
        tileMap.put("openChest", new Tile(9, 6));
        tileMap.put("deadBody", new Tile(0, 15));
        tileMap.put("hammer", new Tile(5, 29));



    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }

    public static void drawWTileWithMargin(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH + x * 2, y * TILE_WIDTH + 2 + y * 2, TILE_WIDTH, TILE_WIDTH);
    }
}
