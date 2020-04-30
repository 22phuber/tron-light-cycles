package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.config.GameColors;
import ch.tron.game.model.GameMode;
import ch.tron.game.model.GameRound;
import ch.tron.game.model.Player;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.gametotransport.LobbyStateUpdateMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a gameMode lobby that holds all available types of tron
 */
public class Lobby implements Runnable {

    private final String id;
    private final String name;
    private final String hostId;
    private final String hostName;
    private final GameMode gameMode;
    private final int numberOfRounds = 5;
    private final int maxPlayers;
    private final boolean visibleToPublic;

    private LobbyStateUpdateMessage lobbyStateUpdateMessage;
    private Map<String, Player> players = new HashMap<>();
    private GameRound gameRound;
    private int roundsPlayed = 0;
    private boolean playing;

    private static final Logger LOGGER = LoggerFactory.getLogger(Lobby.class);

    public Lobby(String id, String name, String hostId, String hostName, GameMode mode, int maxPlayers, boolean visibleToPublic) {
        this.id = id;
        this.name = name;
        this.hostId = hostId;
        this.hostName = hostName;
        this.gameMode = mode;
        this.maxPlayers = maxPlayers;
        this.visibleToPublic = visibleToPublic;

        addPlayer(hostId);

        this.lobbyStateUpdateMessage = new LobbyStateUpdateMessage(id);
    }

    public void run(){

        while(players.size() > 0){

            LOGGER.info("Entered Lobby");

            while(!isPlaying()){
                lobbyStateUpdateMessage.setUpdate(getLobbyState());
                GameManager.getMessageForwarder().forwardMessage(lobbyStateUpdateMessage);
            }

            GameManager.getMessageForwarder().forwardMessage(new GameConfigMessage(
                    id,
                    gameMode.getX(),
                    gameMode.getY(),
                    gameMode.getLineThickness()
            ));

            while(roundsPlayed < numberOfRounds) {

                LOGGER.info("Entered GameRound");

                gameRound = new GameRound(
                        id,
                        (HashMap)players,
                        gameMode.getX(),
                        gameMode.getY()
                );

                gameRound.start();
                roundsPlayed++;
            }

            synchronized (this) { playing = false; }
        }
    }

    //New Players are added to PlayerList in Lobby, they will join in the next GameRound
    public void addPlayer(String playerId) {

        int player_count = players.size();
        if (player_count < maxPlayers) {

            Color[] colors = GameColors.getColors10();

            Player pl = new Player(
                    playerId,
                    "username",
                    50 * player_count,
                    50 * player_count,
                    1,
                    colors[player_count % colors.length]);

            players.put(pl.getId(), pl);
        }
    }

    public void updatePlayer(String playerId, String key) {
        Player pl = players.get(playerId);
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
    }

    public synchronized void play() {
        players.get(hostId).setReady(true);
        this.playing = true;
    }

    public synchronized boolean isPlaying() { return playing; }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public boolean getVisibleToPublic() {
        return visibleToPublic;
    }

    private HashMap getReadyPlayers() {

        Map<String, Player> readyPlayers = new HashMap<>();

        for(Map.Entry<String, Player>player : this.players.entrySet()){
            if(player.getValue().isReady()){
                readyPlayers.put(player.getKey(), player.getValue());
            }
        }

        return (HashMap) readyPlayers;
    }

    private JSONObject getLobbyState() {

        JSONObject players = new JSONObject();
        players.put("subject", "lobbyState");
        JSONArray all = new JSONArray();
        this.players.values().forEach(player -> {
            JSONObject one = new JSONObject();
            try {
                one.put("clientId", player.getId());
                one.put("name", player.getName());
                one.put("ready", player.getReady());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            all.put(one);
        });
        players.put("players", all);
        return players;
    }
}
