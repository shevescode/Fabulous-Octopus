package com.codecool.dungeoncrawl.logic.mapObjects;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class MapObject implements Drawable {
    private Cell cell;

    public MapObject(Cell cell) {
        this.cell = cell;
    }
}
