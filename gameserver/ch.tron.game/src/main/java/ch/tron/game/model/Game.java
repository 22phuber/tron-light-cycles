package ch.tron.game.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a tron game.
 */
public class Game {

    private final String name;

    private final Map<String, GameRound> rounds;

    public Game(String name) {
        this.name = name;
        this.rounds = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addRound(String roundId, GameRound round) {
        rounds.put(roundId, round);
    }

    public Map<String, GameRound> getRounds() {
        return rounds;
    }

}
