package ch.tron.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Wall
 */
class WallTest {

    private Wall wall;

    @BeforeEach
    void initialize(){
        wall = new Wall(100, 100, 0, 0);
    }

    @Test
    void getWidth() {
        assertEquals(100, wall.getWidth());
    }

    @Test
    void setWidth() {
        wall.setWidth(200);
        assertEquals(200, wall.getWidth());
    }

    @Test
    void getHeight() {
        assertEquals(100, wall.getHeight());
    }

    @Test
    void setHeight() {
        wall.setHeight(200);
        assertEquals(200, wall.getHeight());
    }

    @Test
    void getX() {
        assertEquals(0, wall.getX());
    }

    @Test
    void setX() {
        wall.setX(1);
        assertEquals(1, wall.getX());
    }

    @Test
    void getY() {
        assertEquals(0, wall.getY());
    }

    @Test
    void setY() {
        wall.setY(1);
        assertEquals(1, wall.getY());
    }
}