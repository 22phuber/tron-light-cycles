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
        player.addTurn(1);
        assertEquals(1, player.getTurns().get(0).getNewDirection());
    }

    @Test
    void getId() {
        assertEquals("playerId", player.getId());
    }

    @Test
    void posx() {
        player.setPosx(10);
        assertEquals(10, player.getPosx());
    }

    @Test
    void posy() {
        player.setPosy(10);
        assertEquals(10, player.getPosy());
    }

    @Test
    void dir() {
        player.setDir(1);
        assertEquals(1, player.getDir());
    }

    @Test
    void color() {
        assertEquals("rgb(0,0,0)", player.getColor());
        player.setColor("rgb(1,1,1)");
        assertEquals("rgb(1,1,1)", player.getColor());
    }

    @Test
    void ready() {
        assertFalse(player.isReady());
        player.setReady(true);
        assertTrue(player.isReady());
    }

    @Test
    void name() {
        assertEquals("playerName", player.getName());
        player.setName("playerName1");
        assertEquals("playerName1", player.getName());
    }
}