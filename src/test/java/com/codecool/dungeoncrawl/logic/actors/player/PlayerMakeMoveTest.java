package com.codecool.dungeoncrawl.logic.actors.player;


import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlayerMakeMoveTest {
    private GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    @Mock
    private List<Item> inventory;

    @Mock
    private Skeleton skeleton;

    @Test
    public void givenPlayerAndNextMoveCoordinates_whenPlayerMakeMove_thenPlayerCanMoveToNextCell() {

        // given
        Player player = new Player(gameMap.getCell(1, 0));
        int dx = 1;
        int dy = 0;

        //when
        player.playerMakeMove(dx, dy);

        //then
        assertEquals(player, gameMap.getCell(2, 0).getActor());
        assertNull(gameMap.getCell(1, 0).getActor());
    }

    @Test
    public void givenPlayerAndNextMoveCoordinates_whenPlayerMakeMove_thenPlayerCanNotStandOnWall() {
        gameMap.getCell(2,0).setType(CellType.WALL);

        // given
        Player player = new Player(gameMap.getCell(1, 0));
        int dx = 1;
        int dy = 0;

        //when
        player.playerMakeMove(dx, dy);

        //then
        assertNull(gameMap.getCell(2, 0).getActor());
        assertEquals(player, gameMap.getCell(1, 0).getActor());

    }

    @Test
    public void givenPlayerAndNextMoveCoordinates_whenPlayerMakeMove_thenPlayerCanNotStandOnChest() {
        gameMap.getCell(2,0).setType(CellType.CLOSED_CHEST);

        // given
        Player player = new Player(gameMap.getCell(1, 0));
        int dx = 1;
        int dy = 0;
        player.setInventory(inventory);

        //when
        player.playerMakeMove(dx, dy);

        //then
        assertEquals(player, gameMap.getCell(1, 0).getActor());
        assertNull(gameMap.getCell(2, 0).getActor());

    }

    @Test
    public void givenPlayerAndNextMoveCoordinates_whenPlayerMakeMove_thenPlayerCanNotMoveThroughClosedDoor() {
        gameMap.getCell(2,0).setType(CellType.CLOSED_DOOR);

        // given
        Player player = new Player(gameMap.getCell(1, 0));
        int dx = 1;
        int dy = 0;
        player.setInventory(inventory);

        //when
        player.playerMakeMove(dx, dy);

        //then
        assertEquals(player, gameMap.getCell(1, 0).getActor());
        assertNull(gameMap.getCell(2, 0).getActor());

    }

    @Test
    public void givenPlayerAndNextMoveCoordinates_whenPlayerMakeMove_thenPlayerCanMoveThroughOpenDoor() {
        gameMap.getCell(2,0).setType(CellType.OPEN_DOOR);

        // given
        Player player = new Player(gameMap.getCell(1, 0));
        int dx = 1;
        int dy = 0;

        //when
        player.playerMakeMove(dx, dy);

        //then
        assertEquals(player, gameMap.getCell(2, 0).getActor());
        assertNull(gameMap.getCell(1, 0).getActor());

    }

    @Test
    public void givenPlayerAndNextMoveCoordinates_whenPlayerMakeMove_thenPlayerCanStandOnOpenChest() {
        gameMap.getCell(2,0).setType(CellType.OPEN_CHEST);

        // given
        Player player = new Player(gameMap.getCell(1, 0));
        int dx = 1;
        int dy = 0;

        //when
        player.playerMakeMove(dx, dy);

        //then
        assertEquals(player, gameMap.getCell(2, 0).getActor());
        assertNull(gameMap.getCell(1, 0).getActor());

    }

    @Test
    public void givenPlayerAndNextMoveCoordinates_whenPlayerMakeMove_thenPlayerCanNotStandOnSkeleton() {
        gameMap.getCell(2,0).setActor(skeleton);

        // given
        Player player = new Player(gameMap.getCell(1, 0));

        int dx = 1;
        int dy = 0;


        //when
        player.playerMakeMove(dx, dy);

        //then
        assertEquals(player, gameMap.getCell(1, 0).getActor());
        assertEquals(skeleton, gameMap.getCell(2, 0).getActor());

    }


}