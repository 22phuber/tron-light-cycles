package ch.tron.game.model;

import ch.tron.game.GameManager;
import ch.tron.game.controller.Lobby;
import ch.tron.middleman.messagedto.gametotransport.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Template for every GameMode
 */
public abstract class GameMode {

    final Logger logger = LoggerFactory.getLogger(GameMode.class);
    final GameStateUpdateMessage gameStateUpdateMessage;
    final int x;
    final int y;
    final int lineThickness;
    final Map<String, Player> playersAlive;
    final List<String> playersDead = new LinkedList<>();
    final boolean[][] field;
    final int FPS = 60;
    final long LOOP_INTERVAL = 1000000000 / FPS;
    final String lobbyId;
    final int scoreFactor = 1;

    public GameMode(int x, int y, int lineThickness, String lobbyId, Map<String, Player> players){
        this.x = x;
        this.y = y;
        this.lineThickness = lineThickness;
        this.lobbyId = lobbyId;
        this.gameStateUpdateMessage = new GameStateUpdateMessage(lobbyId);
        this.playersAlive = new ConcurrentHashMap<>(players);
        this.field = new boolean[x][y];
    }

    public abstract void start();

    public abstract void move();

    public final void countdown(){
        int count = 3;
        Lobby lobby = GameManager.getLobbies().get(lobbyId);
        final CountdownMessage countdownMsg = new CountdownMessage(
                lobbyId,
                lobby.getRoundsPlayed() + 1,
                lobby.getNumberOfRounds()
        );
        while (count != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(800);
                countdownMsg.setCount(count--);
                GameManager.getMessageForwarder().forwardMessage(countdownMsg);
            } catch (InterruptedException e) {
                logger.info(e.getMessage());
            }
        }
    }

    public final void initialize(){
        GameManager.getMessageForwarder().forwardMessage(new GameConfigMessage(lobbyId, x, y, lineThickness));

        gameStateUpdateMessage.setInitial(true);
        gameStateUpdateMessage.setUpdate(render());
        GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);
        gameStateUpdateMessage.setInitial(false);
    }

    public static final GameMode getGameModeByName(String name, String lobbyId, Map<String, Player> players) {
        if (name.equals("classic")) { return new Classic(lobbyId, players); }
        return null;
    }

    public final JSONObject render() throws JSONException {
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

    public final void updatePlayer(String playerId, String key) {
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

    public final void die(Player pl) {
        playersAlive.remove(pl.getId());
        playersDead.add(pl.getId());
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

    public void sleepForDelta(long now) {
        long delta = System.nanoTime() - now;
        if(delta < LOOP_INTERVAL){
            try {
                Thread.sleep((LOOP_INTERVAL - delta) / 1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendScore() {
        Map<String, Integer> scores = new HashMap<>();
        playersDead.forEach(playerId -> {
            scores.put(playerId, calculateScore(playersDead.indexOf(playerId) + 1));
        });
        GameManager.getMessageForwarder().forwardMessage(new ScoreMessage(lobbyId, scores));
    }

    private int calculateScore(int rank) {
        return rank * scoreFactor;
    }
}
