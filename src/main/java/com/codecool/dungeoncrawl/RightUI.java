package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.mapObjects.Chest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class RightUI extends GridPane {
    private Text lootedItemText;
    private final UIInventory inventory;
    private final Label healthLabel;
    private final Label attackLabel;
    private final Label inventoryLabel;
    private final Button pickUpButton;
    private final Player player;
    private Stage stage;

    public RightUI(Player player) {
        super();
        this.player = player;
        this.healthLabel = new Label();
        this.attackLabel = new Label();
        this.inventoryLabel = new Label();
        this.pickUpButton = new Button("Pick up item");
        this.lootedItemText = new Text();
        this.stage = new Stage();

        setPrefWidth(200);
        setPadding(new Insets(10));
        add(new Label("Health: "), 0, 0);
        add(healthLabel, 1, 0);
        add(new Label("Attack"), 0, 1);
        add(attackLabel, 1, 1);
        add(pickUpButton, 2, 0);
        this.inventory = new UIInventory();
        add(inventory, 0, 2, 2, 1);
        pickUpButton.setFocusTraversable(false);

    }


    public UIInventory getInventory() {
        return inventory;
    }

    public void setHealthLabel() {
        healthLabel.setText("" + player.getHealth());

    }

    public void setAttackLabel() {
        attackLabel.setText("" + player.getAttack());

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
                player.pickUpItem(cell.getItem());
                cell.setItem(null);
                hideButton();
                setAttackLabel();
            }
        });
    }

    public void setLootedItemText(Cell cell) {
        lootedItemText.setText("You have found " + ((Chest) cell.getMapObject()).getItem());
        lootedItemText.setX(50);
        lootedItemText.setY(50);
        Group root = new Group(lootedItemText);
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();


    }

}
