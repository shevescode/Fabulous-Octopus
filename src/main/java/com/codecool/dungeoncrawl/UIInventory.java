package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.mapObjects.Lootable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class UIInventory extends GridPane {
    private Canvas canvas;
    private GraphicsContext context;
    private ObservableList<Item> inventory;
    private Player player;


    public UIInventory() {
        super();
        inventory = FXCollections.observableArrayList();
        inventory.addListener(new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> change) {
                System.out.println("WESZŁO");
                drawEq();
            }
        });
//        drawEq();
//        setItems(inventory);
        setFocusTraversable(false);
    }

    public List<Item> getItems() {
        return inventory;
    }

    private void drawEq() {
        int counter = 0;
        GridPane temporaryLoot = new GridPane();
        for (Item item : inventory) {
            this.canvas = new Canvas(Tiles.TILE_WIDTH + 4, Tiles.TILE_WIDTH + 4);
            this.context = canvas.getGraphicsContext2D();
//            System.out.println(cell.getItem());
            Tiles.drawTile(context, item, 0, 0);
            temporaryLoot.add(canvas, counter++, 0);


        }
        add(temporaryLoot, 0, 0);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
