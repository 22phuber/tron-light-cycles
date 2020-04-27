package ch.tron.game.model;

import ch.tron.game.GameManager;
import ch.tron.game.config.CanvasConfig;
import ch.tron.middleman.messagedto.gametotransport.GameStateUpdateMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a round of a specific
 */
public class GameRound {

    private GameStateUpdateMessage gameStateUpdateMessage;
    private Map<String, Player> players;
    private final Logger logger = LoggerFactory.getLogger(GameRound.class);

    private final double FPS = 60;
    private final double LOOP_INTERVAL = 1000000000 / FPS;

    public GameRound(String lobbyId, HashMap players) {

        this.players = players;
        this.gameStateUpdateMessage = new GameStateUpdateMessage(lobbyId);

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
        logger.info("Game loop running");

        long t_before = System.nanoTime();
        while (true) {
            long t_current = System.nanoTime();
            long t_delta = t_current - t_before;
            if (t_delta >= LOOP_INTERVAL) {
                t_before = t_current;
                players.values().forEach(player -> {
                    switch (player.getDir()) {
                        case 0:
                            player.setPosx((player.getPosx() + 1) % CanvasConfig.WIDTH.value());
                            break;
                        case 1:
                            player.setPosy((player.getPosy() + 1) % CanvasConfig.HEIGHT.value());
                            break;
                        case 2:
                            int x = player.getPosx();
                            if (x == 0) {
                                player.setPosx(CanvasConfig.WIDTH.value());
                            } else {
                                player.setPosx(x - 1);
                            }
                            break;
                        case 3:
                            int y = player.getPosy();
                            if (y == 0) {
                                player.setPosy(CanvasConfig.HEIGHT.value());
                            } else {
                                player.setPosy(y - 1);
                            }
                            break;
                    }
                });
                gameStateUpdateMessage.setUpdate(playersJSON());
                GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);
            }
        }
    }

}
