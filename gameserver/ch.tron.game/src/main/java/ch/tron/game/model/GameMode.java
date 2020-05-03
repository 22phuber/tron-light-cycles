package ch.tron.game.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a tron game.
 */
public abstract class GameMode {

    final int x;
    final int y;
    final int lineThickness;

    public GameMode(int x, int y, int lineThickness){
        this.x = x;
        this.y = y;
        this.lineThickness = lineThickness;
    }

    public abstract void start();

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final int getLineThickness() {
        return lineThickness;
    }

    public static final GameMode getGameModeByName(String name) {
        if (name.equals("classic")) { return new Classic(); }
        return null;
    }
}
