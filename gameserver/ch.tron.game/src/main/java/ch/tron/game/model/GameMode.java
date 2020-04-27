package ch.tron.game.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a tron game.
 */
public enum GameMode {

    CLASSIC("Classic", 400, 400);


    private final String name;
    private final int x;
    private final int y;

    private GameMode(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
