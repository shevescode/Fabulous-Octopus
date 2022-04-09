package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.Witch;
import com.codecool.dungeoncrawl.logic.mapObjects.Chest;
import com.codecool.dungeoncrawl.logic.items.ChestKey;
import com.codecool.dungeoncrawl.logic.items.DoorKey;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String txtFile) {
        InputStream is = MapLoader.class.getResourceAsStream(txtFile);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ' -> cell.setType(CellType.EMPTY);
                        case '#' -> cell.setType(CellType.WALL);
                        case '.' -> cell.setType(CellType.FLOOR);
                        case 'd' -> cell.setType(CellType.CLOSED_DOOR);
                        case 'h' -> cell.setType(CellType.STAIRS_DOWN);
                        case 'u' -> cell.setType(CellType.STAIRS_UP);
                        case 's' -> {
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                        }
                        case '@' -> {
                            cell.setType(CellType.FLOOR);
                            map.setPlayerCell(cell);
                        }
                        case 'x' -> {
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                        }
                        case 'k' -> {
                            cell.setType(CellType.FLOOR);
                            new DoorKey(cell);
                        }
                        case 'a' -> {
                            cell.setType(CellType.FLOOR);
                            new ChestKey(cell);
                        }
                        case 'c' -> {
                            cell.setType(CellType.CHEST);
                            cell.setMapObject(new Chest(cell));
                        }
                        case 'g' -> {
                            cell.setType(CellType.EMPTY);
                            new Ghost(cell);
                        }
                        case 'w' -> {
                            cell.setType(CellType.EMPTY);
                            new Witch(cell);
                        }
                        default -> throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
