package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Monster;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final int width;
    private final int height;
    private int XOffset = 0;
    private int YOffset = 0;
    private Cell[][] cells;


    private Player player;
    private Cell playerCell;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Monster> getAllMonsters() {
        List<Monster> monsterList = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if(cells[x][y].getActor() instanceof Monster) {
                    monsterList.add((Monster) cells[x][y].getActor());
                }
            }
        }
        return monsterList;
    }

    public void setPlayerCell(Cell cell) {
        playerCell = cell;
    }

    public Cell getFirstPlayerCell() {
        return playerCell;
    }

    public void incrementYOffset() {
        if (YOffset + 20 < getHeight()) {
            if (player.getY() > 13 + YOffset) {
                YOffset += 1;
            }
        }
    }

    public void decrementYOffset() {
        if (YOffset - 1 >= 0) {
            if (player.getY() - YOffset < 6) {
                YOffset -= 1;
            }
        }
    }
    public void incrementXOffset() {
        if (XOffset + 25 < getWidth()) {

            if (player.getX() > 18 + XOffset) {
                XOffset += 1;
            }
        }
    }

    public void decrementXOffset() {
        if (XOffset - 1 >= 0) {
            if (player.getX() - XOffset < 6) {
                XOffset -= 1;
            }
        }
    }

    public int getXOffset() {
        return XOffset;
    }

    public int getYOffset() {
        return YOffset;
    }
}
