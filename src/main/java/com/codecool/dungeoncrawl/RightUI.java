package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.mapObjects.Lootable;
import com.codecool.dungeoncrawl.logic.mapObjects.MapObject;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RightUI extends GridPane {

    private UIInventory inventory;
    private Label healthLabel;
    private Label attackLabel;

    private Button pickUpButton;
    private Player player;

    private Canvas canvas;
    private GraphicsContext context;
    private GridPane mainLootGrid;
    private GridPane lootPlaceGrid;


    public RightUI(Player player) {
        super();


        this.player = player;
        this.healthLabel = new Label();
        this.attackLabel = new Label();

        this.pickUpButton = new Button("Pick up item");

        this.mainLootGrid = new GridPane();
        this.lootPlaceGrid = new GridPane();

        setPrefWidth(200);
        setPadding(new Insets(10));
        add(new Label("Health: "), 0, 0);
        add(healthLabel, 1, 0);
        add(new Label("Attack: "), 0, 1);
        add(attackLabel, 1, 1);
        add(pickUpButton, 2, 0);
        this.inventory = new UIInventory();
        add(inventory, 0, 2, 3, 1);
        healthLabel.setText(Integer.toString(player.getHealth()));
        healthLabel.textProperty().bind(Bindings.convert(player.getHealthProperty()));
        attackLabel.setText(Integer.toString(player.getAttack()));
        attackLabel.textProperty().bind(Bindings.convert(player.getAttackProperty()));
        pickUpButton.setFocusTraversable(false);
        add(mainLootGrid, 0, 14, 2, 1);

    }

    public UIInventory getInventory() {
        return inventory;
    }

    public void showPickButton() {
        pickUpButton.setVisible(true);

    }

    public void hideButton() {
        pickUpButton.setVisible(false);
    }

    public void buttonOnClick(Cell cell) {
        pickUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (cell.isItemOnCell()) {
                    player.pickUpItem(cell.getItem());
                    cell.setItem(null);
                }
                hideButton();
            }
        });
    }

    public void drawLoot(Cell cell) {
        int counter = 0;
        for (int j = 0; j < cell.getMapObjects().size(); j++) {
            for (int i = 0; i < ((Lootable) cell.getMapObjects().get(j)).getLootItems().size(); i++) {
                this.canvas = new Canvas(Tiles.TILE_WIDTH + 4, Tiles.TILE_WIDTH + 4);
                this.context = canvas.getGraphicsContext2D();
                Tiles.drawTile(context, ((Lootable) cell.getMapObjects().get(j)).getLootItems().get(i), 0, 0);
                lootPlaceGrid.add(canvas, counter, 0);
                counter += 1;
            }
        }
        mainLootGrid.add(lootPlaceGrid, 0, 0);

    }

    public void clearLootGrids() {
        lootPlaceGrid.getChildren().clear();
        mainLootGrid.getChildren().clear();
    }

//    public void drawLootPlace() {
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                Tiles.drawWTileWithMargin(context1, CellType.valueOf("FLOOR"), j, i);
//            }
//        }
//        lootPlaceGrid.add(canvas, 1, 1);
//        getChildren().remove(lootPlaceGrid);
//    }

    public void addGridEvent(Cell cell) {
        final int[] counter = {0};
        lootPlaceGrid.getChildren().forEach(item -> {
            item.setOnMouseClicked(event -> {
                int clickedLoot = lootPlaceGrid.getChildren().indexOf(event.getPickResult().getIntersectedNode());
                player.pickUpItem(((Lootable) cell.getMapObjects().get(counter[0] - 1)).getLootItems().get(clickedLoot));
                lootPlaceGrid.getChildren().remove(clickedLoot);
                ((Lootable) cell.getMapObjects().get(counter[0] - 1)).removeItem(clickedLoot);
            });
            System.out.println(counter[0]);
            counter[0] += 1;
        });
    }
}