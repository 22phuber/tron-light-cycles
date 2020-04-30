package ch.tron.game.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a tron game.
 */
public enum GameMode {

    CLASSIC("classic", 400, 400, 5);

    private final String name;
    private final int x;
    private final int y;
    private final int lineThickness;

    GameMode(String name, int x, int y, int lineThickness) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.lineThickness = lineThickness;
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

    public int getLineThickness() {
        return lineThickness;
    }

    public static GameMode getGameModeByName(String name) {
        if (name.equals("classic")) { return GameMode.CLASSIC; }
        return null;
    }
}
