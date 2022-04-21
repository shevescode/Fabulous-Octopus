package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.mapObjects.Lootable;
import com.codecool.dungeoncrawl.logic.mapObjects.MapObject;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.reverse;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
    private List<MapObject> mapObjects = new ArrayList<>();
    private final GameMap gameMap;
    private final int x;
    private final int y;


    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Item getItem() {
        return item;
    }

    public List<MapObject> getMapObjects() {
        return mapObjects;
    }

    public void addMapObject(MapObject mapObject) {
        this.mapObjects.add(mapObject);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Cell getNeighbor(int dx, int dy) {
        int cellX = x + dx;
        int cellY = y + dy;
        if(cellX >= 0 && cellX < gameMap.getWidth() && cellY >= 0 && cellY < gameMap.getHeight()){
            return gameMap.getCell(x + dx, y + dy);
        } else {
            return null;
        }

    }

    public List<Item> getAllItemsOnCell() {
        List<Item> itemList = new ArrayList<>();
        for (MapObject mapObject:mapObjects) {
            itemList.addAll(((Lootable) mapObject).getLootItems());
        }
        return itemList;
    }

    public void removeItemFromCell(Item item) {
        try {
            for (MapObject mapObject : mapObjects) {
                ((Lootable) mapObject).getLootItems().remove(item);
            }
        } catch (Exception ignored) {

        }
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public boolean isActorOnCell() {
        return getActor() != null;
    }

    public boolean isItemOnCell() {
        return getItem() != null;
    }
    public boolean isMapObjectOnCell() {
        return getMapObjects().size() != 0;
    }

    public boolean isNextCellOnMap(Cell cell) {
        return cell != null;
    }
}
