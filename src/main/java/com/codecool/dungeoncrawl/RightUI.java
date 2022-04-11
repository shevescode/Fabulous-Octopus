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
    private GraphicsContext context;
    private GridPane chestLootGrid;



    public RightUI(Player player) {
        super();
//        this.setGridLinesVisible(true);
        this.player = player;
        this.healthLabel = new Label();
        this.attackLabel = new Label();

        this.pickUpButton = new Button("Pick up item");

        this.stage = new Stage();

        this.canvas = new Canvas(Tiles.TILE_WIDTH, Tiles.TILE_WIDTH);
        this.context = canvas.getGraphicsContext2D();
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
        add(label, 0,3,3,1);
        pickUpButton.setFocusTraversable(false);
        addChestLootLabel();

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
        add(new Label("Chest loot: "), 0, 12);
        add(chestLootGrid, 0, 13, 2, 1);
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
                } else {
                    player.pickUpItem(((Chest) cell.getMapObject()).getItem());
                    ((Chest) cell.getMapObject()).removeItem();

                }
                setAttackLabel();
//                setHealthLabel();
                hideButton();
                clearChestLootGrid();
            }
        });
    }

    public void checkChestLoot(Cell cell) {
        if (((Chest) cell.getMapObject()).isNotEmpty()) {
//            addChestLootLabel();TODO: zmienić żeby chestlootlabel nie wyświetlał się od początku gry
            Tiles.drawTile(context, ((Chest) cell.getMapObject()).getItem(), 0, 0);
            chestLootGrid.add(canvas, 0, 0);
        } else {
            System.out.println("empty"); /*TODO:dorobić coś co bedzie obsługiwało wyjątek pustej skrzynki*/
        }

    }

    public void clearChestLootGrid() {
        chestLootGrid.getChildren().clear();
    }

}