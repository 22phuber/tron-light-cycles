package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.model.GameMode;
import ch.tron.game.model.Player;
import ch.tron.middleman.messagedto.gametotransport.CountdownMessage;
import ch.tron.middleman.messagedto.gametotransport.DeathMessage;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.gametotransport.GameStateUpdateMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    private final Map<String, Player> playersAlive;
    private final GameMode mode;
    private final boolean[][] field;

    private final double FPS = 60;
    private final double LOOP_INTERVAL = 1000000000 / FPS;

    public GameRound(String lobbyId, HashMap players, GameMode mode) {
        this.lobbyId = lobbyId;
        this.playersAlive = new ConcurrentHashMap<>(players);
        this.gameStateUpdateMessage = new GameStateUpdateMessage(lobbyId);
        this.mode = mode;
        this.field = new boolean[mode.getX()][mode.getY()];
    }

    public JSONObject playersJSON() throws JSONException {
        JSONObject state = new JSONObject();
        state.put("subject", "gameState")
                .put("gameId", lobbyId);
        JSONArray all = new JSONArray();
        this.playersAlive.values().forEach(player -> {
            JSONObject one = new JSONObject();
            try {
                one.put("id", player.getId());
                one.put("posx", player.getPosx());
                one.put("posy", player.getPosy());
                one.put("dir", player.getDir());
                one.put("color", player.getColor());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            all.put(one);
        });
        state.put("players", all);
        return state;
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
        while (playersAlive.size() > 0) {
            long t_current = System.nanoTime();
            long t_delta = t_current - t_before;
            if (t_delta >= LOOP_INTERVAL) {
                t_before = t_current;
                playersAlive.values().forEach(player -> {
                    int posx = player.getPosx();
                    int posy = player.getPosy();
                    int lineThickness = mode.getLineThickness();
                    switch (player.getDir()) {
                        case 0:
                            if (posx != mode.getX()) {
                                player.setPosx(posx + lineThickness);
                            }
                            break;
                        case 1:
                            if (posy != mode.getY()) {
                                player.setPosy(posy + lineThickness);
                            }
                            break;
                        case 2:
                            if (posx != 0) {
                                player.setPosx(posx - lineThickness);
                            }
                            break;
                        case 3:
                            if (posy != 0) {
                                player.setPosy(posy - lineThickness);
                            }
                            break;
                    }
                    posx = player.getPosx();
                    posy = player.getPosy();
                    if (posx % mode.getX() == 0 || posy % mode.getY() == 0 || field[posx][posy]) {
                        die(player);
                    }
                    else {
                        for (int i = 0; i < mode.getLineThickness(); i++) {
                            for (int j = 0; j < mode.getLineThickness(); j++) {
                                field[player.getPosx() + i][player.getPosy() + j] = true;
                            }
                        }
                    }
                });
                gameStateUpdateMessage.setUpdate(playersJSON());
                GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);
            }
        }

    }

    public void updatePlayer(String playerId, String key) {
        Player pl = playersAlive.get(playerId);
        if (pl != null) {
            int pl_dir = pl.getDir();
            // HTML5 canvas coordinate system default setting
            // referenced by dir:
            //            (dir = 3)
            //                |
            //                |
            // (dir = 2) ----------- x (dir = 0)
            //                |
            //                |
            //                y
            //            (dir = 1)
            switch (key) {
                case "ArrowLeft":
                    pl_dir = (pl_dir != 0)? 2 : 0;
                    break;
                case "ArrowRight":
                    pl_dir = (pl_dir != 2)? 0 : 2;
                    break;
                case "ArrowUp":
                    pl_dir = (pl_dir != 1)? 3 : 1;
                    break;
                case "ArrowDown":
                    pl_dir = (pl_dir != 3)? 1 : 3;
                    break;
                default: // do nothing
            }
            pl.setDir(pl_dir);
            pl.addTurn(pl.getPosx(), pl.getPosy(), pl_dir);
        }
    }

    private void die(Player pl) {
        playersAlive.remove(pl.getId());
        JSONArray all = new JSONArray();
        pl.getTurns().forEach(turn -> {
            JSONObject one = new JSONObject()
                    .put("posx", turn.getPosx())
                    .put("posy", turn.getPosy())
                    .put("newDirection", turn.getNewDirection());
            all.put(one);
        });
        GameManager.getMessageForwarder().forwardMessage(new DeathMessage(
                lobbyId, pl.getId(), pl.getPosx(), pl.getPosy(), all
        ));
    }
}