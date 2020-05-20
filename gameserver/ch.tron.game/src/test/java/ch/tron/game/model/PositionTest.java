package ch.tron.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Position Class
 */
class PositionTest {

    private Position position;

    @BeforeEach
    void initialize(){
        position = new Position(0,0);
    }

    @Test
    void getX() {
        assertEquals(0, position.getX());
    }

    @Test
    void setX() {
        position.setX(1);
        assertEquals(1, position.getX());
    }

    @Test
    void getY() {
        assertEquals(0, position.getY());
    }

    @Test
    void setY() {
        position.setY(1);
        assertEquals(1, position.getY());
    }
}