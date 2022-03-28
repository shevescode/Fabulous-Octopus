package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class RightUI extends GridPane {
    private UIInventory inventory;
    private Label healthLabel;
    private Label attackLabel;
    private Label inventoryLabel;
    private Button button;
    private Player player;

    public RightUI(Player player) {
        super();
        this.player = player;
        this.healthLabel = new Label();
        this.attackLabel = new Label();
        this.inventoryLabel = new Label();
        this.button = new Button("Pick up item");

        setPrefWidth(200);
        setPadding(new Insets(10));
        // first row
        add(new Label("Health: "), 0, 0);
        add(healthLabel, 1, 0);
        add(new Label("Attack"), 0, 1);
        add(attackLabel, 1, 1);
        add(button, 2, 0);
        // second row
//        ui.add(new Label("Inventory: "), 0, 2);
        this.inventory = new UIInventory();
        add(inventory, 0, 2, 2, 1);
        button.setFocusTraversable(false);

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
        button.setVisible(true);
    }

    public void hideButton() {
        button.setVisible(false);
    }

    public void buttonOnClick(Cell cell) {
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                player.pickUpItem(cell.getItem());
                cell.setItem(null);
               hideButton();
               setAttackLabel();
            }
        });
    }

}
