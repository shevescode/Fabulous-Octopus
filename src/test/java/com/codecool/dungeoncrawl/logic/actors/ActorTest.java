package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CombatTest {
    @Mock
    private Cell playerCell;

    @Mock
    private Cell monsterCell;

    @Test
    public void givenPlayerAndSkeleton_whenCombat_thenSkeletonDiesFirst() {
        //given
        Player player = new Player(playerCell);
        Actor skeleton = new Skeleton(monsterCell);
        player.setAttack(10);
        player.setHealth(10);
        skeleton.setAttack(10);
        skeleton.setHealth(10);
        //when
        player.combat(skeleton);
        //then
        assertEquals(10, player.getHealth());
        assertEquals(10, player.getAttack());
        assertEquals(0, skeleton.getHealth());
        assertEquals(10, skeleton.getAttack());
    }
    @Test
    public void givenPlayerAndSkeletonWithMaxAttack_whenCombat_thenPlayerDies() {
        //given
        Player player = new Player(playerCell);
        Actor skeleton = new Skeleton(monsterCell);
        player.setAttack(0);
        player.setHealth(-5);
        skeleton.setAttack(Integer.MAX_VALUE);
        skeleton.setHealth(10);
        //when
        player.combat(skeleton);
        //then
        assertEquals(0, player.getHealth());
        assertEquals(0, player.getAttack());
        assertEquals(10, skeleton.getHealth());
        assertEquals(Integer.MAX_VALUE, skeleton.getAttack());
    }

    @Test
    public void givenPlayerAndSkeletonWithMinusAttack_whenCombat_thenPlayerHealthNotChanged() {
        //given
        Player player = new Player(playerCell);
        Actor skeleton = new Skeleton(monsterCell);
        player.setAttack(0);
        player.setHealth(10);
        skeleton.setAttack(-10);
        skeleton.setHealth(10);
        //when
        player.combat(skeleton);
        //then
        assertEquals(10, player.getHealth());
        assertEquals(0, player.getAttack());
        assertEquals(10, skeleton.getHealth());
        assertEquals(-10, skeleton.getAttack());
    }

    @Test
    public void givenPlayerAndSkeletonWithSameStats_whenCombat_thenBothSurvivedWithHalfHealth() {
        //given
        Player player = new Player(playerCell);
        Actor skeleton = new Skeleton(monsterCell);
        player.setAttack(5);
        player.setHealth(10);
        skeleton.setAttack(5);
        skeleton.setHealth(10);
        //when
        player.combat(skeleton);
        //then
        assertEquals(5, player.getHealth());
        assertEquals(5, player.getAttack());
        assertEquals(5, skeleton.getHealth());
        assertEquals(5, skeleton.getAttack());
    }


}