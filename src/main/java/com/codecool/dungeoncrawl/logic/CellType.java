package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    STAIRS_DOWN("stairsDown"),
    STAIRS_UP("stairsUp"),
    CLOSED_DOOR("closedDoor"),
    OPEN_DOOR("openDoor"),
    CLOSED_CHEST("closedChest"),
    OPEN_CHEST("openChest");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
