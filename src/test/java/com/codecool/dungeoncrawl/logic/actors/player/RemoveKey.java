package com.codecool.dungeoncrawl.logic.actors.player;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.ChestKey;
import com.codecool.dungeoncrawl.logic.items.DoorKey;
import com.codecool.dungeoncrawl.logic.items.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RemoveKeyTest {

    @Mock
    private Cell cell;

    @Mock
    private ChestKey chestKey;

    @Mock
    private DoorKey doorKey;


    @Test
    public void shouldChestKeyNotInInventory() {
        //given
        Player player = new Player(cell);
        List<Item> inventory = new ArrayList<>();
        player.setInventory(inventory);
        inventory.add(chestKey);

        //when
        player.removeKey(ChestKey.class);

        //then
        assertFalse(inventory.contains(chestKey));
    }

    @Test
    public void shouldDoorKeyNotInInventory() {
        //given
        Player player = new Player(cell);
        List<Item> inventory = new ArrayList<>();
        player.setInventory(inventory);
        inventory.add(doorKey);

        //when
        player.removeKey(DoorKey.class);

        //then
        assertFalse(inventory.contains(doorKey));
    }

    @Test
    public void shouldChestKeyInInventory() {
        //given
        Player player = new Player(cell);
        List<Item> inventory = new ArrayList<>();
        player.setInventory(inventory);
        inventory.add(chestKey);

        //when
        player.removeKey(DoorKey.class);

        //then
        assertTrue(inventory.contains(chestKey));
    }

    @Test
    public void shouldDoorKeyInInventory() {
        //given
        Player player = new Player(cell);
        List<Item> inventory = new ArrayList<>();
        player.setInventory(inventory);
        inventory.add(doorKey);

        //when
        player.removeKey(ChestKey.class);

        //then
        assertTrue(inventory.contains(doorKey));
    }
}