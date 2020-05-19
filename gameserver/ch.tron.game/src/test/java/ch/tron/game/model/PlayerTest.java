package ch.tron.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Player Class
 */
class PlayerTest {

    private Player player;

    @BeforeEach
    void initialize(){
        player = new Player("playerId", "playerName", "rgb(0,0,0)");
    }

    @Test
    void addTurn() {
    }

    @Test
    void getId() {
        assertEquals("playerId", player.getId());
    }

    @Test
    void getPosx() {
    }

    @Test
    void setPosx() {
    }

    @Test
    void getPosy() {
    }

    @Test
    void setPosy() {
    }

    @Test
    void getDir() {
    }

    @Test
    void setDir() {
    }

    @Test
    void getColor() {
        assertEquals("rgb(0,0,0)", player.getColor());
    }

    @Test
    void setColor() {
    }

    @Test
    void isReady() {
        assertFalse(player.isReady());
    }

    @Test
    void setReady() {
    }

    @Test
    void getReady() {
    }

    @Test
    void getTurns() {
    }

    @Test
    void getName() {
        assertEquals("playerName", player.getName());
    }

    @Test
    void setName() {
    }
}