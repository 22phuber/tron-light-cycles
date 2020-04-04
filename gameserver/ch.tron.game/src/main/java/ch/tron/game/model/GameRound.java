package ch.tron.game.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameRound {

    private final String id;

    private final Set<String> group = new HashSet<>();
    private final Map<String, Player> players = new HashMap<>();

    public GameRound(String gameRoundId) {
        this.id = gameRoundId;
    }

    public void addPlayer(Player pl) {
        group.add(pl.getId());
        players.put(pl.getId(), pl);
    }

    public String getId() {
        return id;
    }

    public Set<String> getGroup() {
        return group;
    }

    public Map<String, Player> playersMap() {
        return players;
    }

}
