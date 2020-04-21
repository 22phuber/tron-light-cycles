package ch.tron.game.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a round of a specific {@link ch.tron.game.model.Game}.
 */
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

    public JSONObject playersJSON() throws JSONException {
        JSONObject players = new JSONObject();
        players.put("subject", "player update");
        JSONArray all = new JSONArray();
        this.players.values().forEach(player -> {
            JSONObject one = new JSONObject();
            try {
                one.put("id", player.getId());
                one.put("posx", player.getPosx());
                one.put("posy", player.getPosy());
                one.put("dir", player.getDir());
                one.put("color", awtColorToString(player.getColor()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            all.put(one);
        });
        players.put("players", all);
        return players;
    }

    private String awtColorToString(Color c) {
        StringBuilder sb = new StringBuilder();
        sb.append("rgb(")
                .append(c.getRed())
                .append(",")
                .append(c.getGreen())
                .append(",")
                .append(c.getBlue())
                .append(")");
        return sb.toString();
    }

    public void start(){

    }

}
