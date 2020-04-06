package ch.tron.game.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a game lobby that holds all available types of tron {@link Game}.
 */
public class Lobby {

    private final Map<String, Game> games;

    public Lobby() {
        games = new HashMap<>();
    }

    public Map<String, Game> getGames() {
        return games;
    }
}
