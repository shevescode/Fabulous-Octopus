package com.codecool.dungeoncrawl.logic;

import javafx.scene.paint.Paint;

public enum CellType implements Drawable {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    STAIRS_DOWN("stairsDown"),
    STAIRS_UP("stairsUp"),
    CLOSED_DOOR("closedDoor"),
    OPEN_DOOR("openDoor"),
    CHEST("chest");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
