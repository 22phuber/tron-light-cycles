package ch.tron.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for WallCleaner
 */
class WallCleanerTest {

    private WallCleaner wallCleaner;

    @BeforeEach
    void initialize(){
        wallCleaner = new WallCleaner(1);
        wallCleaner.addPosition(new Position(0,1));
    }

    @Test
    void position() {
        Position position = wallCleaner.getPosition();
        assertEquals(0, position.getX());
        assertEquals(1, position.getY());
        assertNull(wallCleaner.getPosition());
    }


    @Test
    void peekPosition() {
        Position position = wallCleaner.peekPosition();
        assertEquals(0, position.getX());
        assertEquals(1, position.getY());
        assertNotNull(wallCleaner.getPosition());
    }
}