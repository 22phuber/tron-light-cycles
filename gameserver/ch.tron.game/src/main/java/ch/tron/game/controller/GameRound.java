package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.model.GameMode;
import ch.tron.game.model.Player;
import ch.tron.middleman.messagedto.gametotransport.CountdownMessage;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.gametotransport.GameStateUpdateMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a round of a specific
 */
public class GameRound {

    private final Logger logger = LoggerFactory.getLogger(GameRound.class);

    private GameStateUpdateMessage gameStateUpdateMessage;

    private final String lobbyId;
    private final Map<String, Player> players;
    private final GameMode mode;
    private final Boolean[][] field;

    private final double FPS = 60;
    private final double LOOP_INTERVAL = 1000000000 / FPS;

    public GameRound(String lobbyId, HashMap players, GameMode mode) {
        this.lobbyId = lobbyId;
        this.players = players;
        this.gameStateUpdateMessage = new GameStateUpdateMessage(lobbyId);
        this.mode = mode;
        this.field = new Boolean[mode.getX()][mode.getY()];
    }

    public JSONObject playersJSON() throws JSONException {
        JSONObject state = new JSONObject();
        state.put("subject", "gameState")
                .put("gameId", lobbyId);
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
        state.put("players", all);
        return state;
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

    public void start() {

        GameManager.getMessageForwarder().forwardMessage(new GameConfigMessage(
                lobbyId,
                mode.getX(),
                mode.getY(),
                mode.getLineThickness()
        ));

        gameStateUpdateMessage.setInitial(true);
        gameStateUpdateMessage.setUpdate(playersJSON());
        GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);
        gameStateUpdateMessage.setInitial(false);

        int count = 3;
        final CountdownMessage countdownMsg = new CountdownMessage(lobbyId);
        while (count != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(800);
                countdownMsg.setCount(count--);
                GameManager.getMessageForwarder().forwardMessage(countdownMsg);
            } catch (InterruptedException e) {
                logger.info(e.getMessage());
            }
        }

        logger.info("Game round started");

        long t_before = System.nanoTime();
        while (true) {
            long t_current = System.nanoTime();
            long t_delta = t_current - t_before;
            if (t_delta >= LOOP_INTERVAL) {
                t_before = t_current;
                players.values().forEach(player -> {
                    switch (player.getDir()) {
                        case 0:
                            player.setPosx((player.getPosx() + 1) % mode.getX());
                            break;
                        case 1:
                            player.setPosy((player.getPosy() + 1) % mode.getY());
                            break;
                        case 2:
                            int x = player.getPosx();
                            if (x == 0) {
                                player.setPosx(mode.getX());
                            } else {
                                player.setPosx(x - 1);
                            }
                            break;
                        case 3:
                            int y = player.getPosy();
                            if (y == 0) {
                                player.setPosy(mode.getY());
                            } else {
                                player.setPosy(y - 1);
                            }
                            break;
                    }
                    //field[player.getPosx()][player.getPosy()] = true;
                });
                gameStateUpdateMessage.setUpdate(playersJSON());
                GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);
            }
        }
    }
}
