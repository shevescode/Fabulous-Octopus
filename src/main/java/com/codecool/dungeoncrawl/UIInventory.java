package com.codecool.dungeoncrawl;


import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class UIInventory extends ListView<Item> {
    private ObservableList<Item> inventory;

    public UIInventory() {
        super();
        inventory = FXCollections.observableArrayList();
        setItems(inventory);
        setFocusTraversable(false);
    }


}
