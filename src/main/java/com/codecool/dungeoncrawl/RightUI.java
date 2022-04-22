package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.mapObjects.Lootable;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.math.BigInteger;
import java.util.List;


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

        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
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
//        lootLayout();
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
                this.canvas = new Canvas(Tiles.TILE_WIDTH, Tiles.TILE_WIDTH);
                this.context = canvas.getGraphicsContext2D();

//                context.setFill(Color.BLACK);
//                context.fillRect(0, 0, canvas.getWidth()*2, canvas.getHeight()*2);
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
        List<Item> itemList = cell.getAllItemsOnCell();

        lootPlaceGrid.getChildren().forEach(item -> {
            item.setOnMouseClicked(event -> {
                int clickedLoot = lootPlaceGrid.getChildren().indexOf(event.getPickResult().getIntersectedNode());
                cell.removeItemFromCell(itemList.get(clickedLoot));
                player.pickUpItem(itemList.get(clickedLoot));
                lootPlaceGrid.getChildren().remove(clickedLoot);
            });
        });
    }


//    private void lootLayout() {
//        mainLootGrid.setPrefSize(4 * Tiles.TILE_WIDTH, 100);
//        mainLootGrid.setMargin(this, new Insets(100, 0, 0, 0));
//        mainLootGrid.setPadding(new Insets(5));
//        mainLootGrid.setBorder(new Border(new BorderStroke(Color.BLACK,
//                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
//
//        mainLootGrid.setBackground(new Background(new BackgroundFill(Color.valueOf("#472D3C"), CornerRadii.EMPTY, Insets.EMPTY)));
//    }

}