package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.model.GameMode;
import ch.tron.game.model.Player;
import ch.tron.middleman.messagedto.gametotransport.LobbyStateUpdateMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a gameMode lobby that holds all available types of tron
 */
public class Lobby implements Runnable {

    private final String id;
    private final String name;
    private final String hostId;
    private final GameMode game;
    private final int numberOfRounds = 5;
    private final int maxPlayers;
    private final boolean visibleToPublic;

    private final int FPS = 10;
    private final long LOOP_INTERVAL = 1000000000 / FPS;
    private long now;
    private long delta;

    private LobbyStateUpdateMessage lobbyStateUpdateMessage;
    private Map<String, Player> players = new HashMap<>();
    private int roundsPlayed = 0;
    private boolean playing;

    private static final Logger LOGGER = LoggerFactory.getLogger(Lobby.class);

    public Lobby(String id, String name, String hostId, String hostColor, String mode, int maxPlayers, boolean visibleToPublic) {
        this.id = id;
        this.name = name;
        this.hostId = hostId;
        this.maxPlayers = maxPlayers;
        this.visibleToPublic = visibleToPublic;

        addPlayer(hostId, hostColor);

        this.game = GameMode.getGameModeByName(mode, this.id, this.players);
        this.lobbyStateUpdateMessage = new LobbyStateUpdateMessage(id);
    }

    public void run() {

        while(players.size() > 0){

            LOGGER.info("Entered Lobby");

            while(!isPlaying()){
                now = System.nanoTime();
                lobbyStateUpdateMessage.setUpdate(getLobbyState());
                GameManager.getMessageForwarder().forwardMessage(lobbyStateUpdateMessage);
                delta = System.nanoTime() - now;
                if(delta < LOOP_INTERVAL){
                    try {
                        Thread.sleep((LOOP_INTERVAL - delta) / 1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            while(roundsPlayed < numberOfRounds) {
                resetPlayers();
                game.start();
                roundsPlayed++;
            }

            synchronized (this) { playing = false; }
        }
    }

    //New Players are added to PlayerList in Lobby, they will join in the next GameRound
    public void addPlayer(String playerId, String color) {

        int player_count = players.size();
        if (player_count < maxPlayers) {

            Player pl = new Player(
                    playerId,
                    50 * player_count + 50,
                    50,
                    1,
                    color);

            players.put(pl.getId(), pl);
        }
    }

    public void updatePlayer(String playerId, String key) {
        game.updatePlayer(playerId, key);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public GameMode getGame() {
        return game;
    }

    public synchronized void play() {
        players.get(hostId).setReady(true);
        this.playing = true;
    }

    public int getPlayersJoined() { return players.size(); }

    public synchronized boolean isPlaying() { return playing; }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public boolean isVisibleToPublic() {
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
                one.put("ready", player.getReady());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            all.put(one);
        });
        players.put("players", all);
        return players;
    }

    private void resetPlayers() {
        int x = 0;
        int y = 50;
        for (Map.Entry<String, Player> player: players.entrySet()) {
            x += 50;
            Player pl = player.getValue();
            pl.setPosx(x);
            pl.setPosy(y);
            pl.setDir(1);

            pl.getTurns().clear();
        }
    }
}
