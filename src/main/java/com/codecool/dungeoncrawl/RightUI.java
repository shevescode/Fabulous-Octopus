package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.mapObjects.Chest;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class RightUI extends GridPane {

    private UIInventory inventory;
    private Label healthLabel;
    private Label attackLabel;

    private Button pickUpButton;
    private Player player;
    private Stage stage;

    private Canvas canvas;
    private Canvas canvas1;
    private GraphicsContext context;
    private GraphicsContext context1;
    private GridPane chestLootGrid;
    private GridPane lootPlaceGrid;


    public RightUI(Player player) {
        super();

        this.player = player;
        this.healthLabel = new Label();
        this.attackLabel = new Label();

        this.pickUpButton = new Button("Pick up item");

        this.stage = new Stage();

        this.chestLootGrid = new GridPane();

        setPrefWidth(200);
        setPadding(new Insets(10));
        add(new Label("Health: "), 0, 0);
        add(healthLabel, 1, 0);
        add(new Label("Attack: "), 0, 1);
        add(attackLabel, 1, 1);
        add(pickUpButton, 2, 0);
        this.inventory = new UIInventory();
        add(inventory, 0, 2, 3, 1);
        Label label = new Label();
        healthLabel.setText(Integer.toString(player.getHealth()));
        healthLabel.textProperty().bind(Bindings.convert(player.getHealthProperty()));
        add(label, 0, 3, 3, 1);
        pickUpButton.setFocusTraversable(false);
        addChestLootLabel();

        this.lootPlaceGrid = new GridPane();

    }


    public UIInventory getInventory() {
        return inventory;
    }

    //    public void setHealthLabel() {
//        healthLabel.setText("" + player.getHealth());
//
//    }


    public void setAttackLabel() {
        attackLabel.setText("" + player.getAttack());

    }

    public void addChestLootLabel() {
        add(new Label("LOOT: "), 0, 13);
        add(chestLootGrid, 0, 14, 2, 1);
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
//                    setAttackLabel();
                }
//                } else {
//                    addAllItemsToInv(cell);
//                }
                setAttackLabel();
//                setHealthLabel();
                hideButton();
                clearChestLootGrid();
            }
        });
    }

    private void addAllItemsToInv(Cell cell) {
        for (int i = 0; i < ((Chest) cell.getMapObject()).getItemsInChest().size(); i++) {
            player.pickUpItem(((Chest) cell.getMapObject()).getItemsInChest().get(i));

        }
//        ((Chest) cell.getMapObject()).removeItems();
        clearChestLootGrid();

    }

    public void checkChestLoot(Cell cell) {
        for (int i = 0; i < ((Chest) cell.getMapObject()).getItemsInChest().size(); i++) {
            this.canvas = new Canvas(Tiles.TILE_WIDTH * 4 + 8, Tiles.TILE_WIDTH * 2 + 4);
            this.context = canvas.getGraphicsContext2D();

            Tiles.drawWTileWithMargin(context, ((Chest) cell.getMapObject()).getItemsInChest().get(i), i, 0);

            lootPlaceGrid.add(canvas, i, 0);
        }
        chestLootGrid.add(lootPlaceGrid, 0, 0);
    }

    public void clearChestLootGrid() {
        chestLootGrid.getChildren().clear();

//        Tiles.drawWTileWithMargin(context, ((Chest) cell.getMapObject()).getItem(), 0, 0);
//        Tiles.drawWTileWithMargin(context, ((Chest) cell.getMapObject()).getItem(), 1, 0);
//        Tiles.drawWTileWithMargin(context, ((Chest) cell.getMapObject()).getItem(), 2, 0);
//        Tiles.drawWTileWithMargin(context, ((Chest) cell.getMapObject()).getItem(), 3, 0);
//
//        Tiles.drawWTileWithMargin(context, ((Chest) cell.getMapObject()).getItem(), 0, 1);
//        Tiles.drawWTileWithMargin(context, ((Chest) cell.getMapObject()).getItem(), 1, 1);
//        Tiles.drawWTileWithMargin(context, ((Chest) cell.getMapObject()).getItem(), 2, 1);
//        Tiles.drawWTileWithMargin(context, ((Chest) cell.getMapObject()).getItem(), 3, 1);
//        chestLootGrid.setGridLinesVisible(true);
//        chestLootGrid.add(canvas, 0, 0);

    }

//    public void drawLootPlace() {
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                Tiles.drawWTileWithMargin(context1, CellType.valueOf("FLOOR"), j, i);
//
//            }
//
//        }
//        lootPlaceGrid.add(canvas, 1, 1);
//        getChildren().remove(lootPlaceGrid);
//    }

    public void addGridEvent(Cell cell) {
        System.out.println(lootPlaceGrid.getChildren().size() + "ROZMIAR");

        lootPlaceGrid.getChildren().forEach(item -> {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int clickedLoot = lootPlaceGrid.getChildren().indexOf(event.getPickResult().getIntersectedNode());
                    if (event.getClickCount() == 1) {

                        for (int i = 0; i < chestLootGrid.getChildren().size(); i++) {
                            if (chestLootGrid.getChildren().indexOf(event.getPickResult().getIntersectedNode()) == ((Chest) cell.getMapObject()).getItemsInChest().indexOf(i)) {
                                player.pickUpItem(((Chest) cell.getMapObject()).getItemsInChest().get(clickedLoot));
                                lootPlaceGrid.getChildren().remove(clickedLoot);
                            }

                        }
                        ((Chest) cell.getMapObject()).removeItem(clickedLoot);
//                        ((Chest) cell.getMapObject()).removeItems();
                    }

                }
            });
        });
    }
}