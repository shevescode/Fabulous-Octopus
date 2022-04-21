package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;

import java.util.List;

public class UIInventory extends GridPane {
    private Canvas canvas;
    private GraphicsContext context;
    private ObservableList<Item> inventory;
    private Player player;
//    private GridPane temporaryLoot;


    public UIInventory() {
        super();
        inventory = FXCollections.observableArrayList();

        inventory.addListener(new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> change) {
                drawInventory();
            }
        });
    }

    public List<Item> getItems() {
        return inventory;
    }

    private void drawInventory() {
        GridPane temporaryLoot = new GridPane();
        clearUInventoryGrid();
        int counter = 0;

        for (Item item : inventory) {
            this.canvas = new Canvas(Tiles.TILE_WIDTH + 4, Tiles.TILE_WIDTH + 4);
            this.context = canvas.getGraphicsContext2D();
            Tiles.drawTile(context, item, 0, 0);
            temporaryLoot.add(canvas, counter++, 0);
        }
        add(temporaryLoot, 0, 0);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void clearUInventoryGrid() {
        this.getChildren().clear();
    }

    public void removeCanvasItemFromInv(Item item) {
        this.getChildren().remove(item);
    }


}
