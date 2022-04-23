package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;


public class UIInventory extends GridPane {
    private Canvas canvas;
    private GraphicsContext context;
    private ObservableList<Item> inventory;
    private Player player;

    public UIInventory() {
        super();
        inventory = FXCollections.observableArrayList();
        inventoryLayout();
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void removeCanvasItemFromInv(Item item) {
        this.getChildren().remove(item);
    }

    private void drawInventory() {
        GridPane temporaryLoot = new GridPane();
        clearUInventoryGrid();
        int counterCol = 0;
        int counterRow = 0;

        for (Item item : inventory) {
            this.canvas = new Canvas(Tiles.TILE_WIDTH + 4, Tiles.TILE_WIDTH + 4);
            this.context = canvas.getGraphicsContext2D();
            Tiles.drawTile(context, item, 0, 0);
            if (counterCol % 4 == 0) {
                counterCol = 0;
                counterRow++;
            }
            temporaryLoot.add(canvas, counterCol++, counterRow);
        }
        add(temporaryLoot, 0, 0);
    }

    private void clearUInventoryGrid() {
        this.getChildren().clear();
    }

    private void inventoryLayout() {
        setPrefSize(5 * Tiles.TILE_WIDTH, 200);
        setMargin(this, new Insets(10, 0, 50, 0));
        setPadding(new Insets(5));
        setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

        this.setBackground(new Background(new BackgroundFill(Color.valueOf("#472D3C"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
