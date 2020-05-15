package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.model.Player;
import ch.tron.middleman.messagedto.gametotransport.LobbyStateUpdateMessage;
import ch.tron.middleman.messagedto.gametotransport.TerminateGameMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * Represents a lobby that holds all available types of tron
 */
public class Lobby implements Runnable {

    private final String id;
    private final String name;
    private String hostId;
    private final String mode;
    private final int maxPlayers;
    private final boolean visibleToPublic;

    private GameMode game;

    private final int numberOfRounds = 5;
    private final LobbyStateUpdateMessage lobbyStateUpdateMessage;
    private final Map<String, Player> players = new ConcurrentHashMap<>();

    private int roundsPlayed = 0;
    private boolean playing = false;

    private static final Logger LOGGER = LoggerFactory.getLogger(Lobby.class);

    public Lobby(String id,
                 String name,
                 String hostName,
                 String hostId,
                 String hostColor,
                 String mode,
                 int maxPlayers,
                 boolean visibleToPublic) {
        this.id = id;
        this.name = name;
        this.hostId = hostId;
        this.maxPlayers = maxPlayers;
        this.visibleToPublic = visibleToPublic;
        this.mode = mode;

        this.game = getGameModeByName(this.mode, this.id, this.players);

        addPlayer(hostId, hostName, hostColor);

        this.lobbyStateUpdateMessage = new LobbyStateUpdateMessage(id);
    }

    public void run() {

        while (players.size() > 0){

            LOGGER.info("Entered Lobby {}", id);

            roundsPlayed = 0;

            while (!isPlaying() && players.size() > 0) {
                long now = System.nanoTime();
                lobbyStateUpdateMessage.setUpdate(getLobbyState());
                GameManager.getMessageForwarder().forwardMessage(lobbyStateUpdateMessage);
                long delta = System.nanoTime() - now;
                int FPS = 1;
                long LOOP_INTERVAL = 1000000000 / FPS;
                if(delta < LOOP_INTERVAL){
                    try {
                        Thread.sleep((LOOP_INTERVAL - delta) / 1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            while (roundsPlayed < numberOfRounds && players.size() > 0) {
                resetPlayers();
                game = getGameModeByName(mode, id, getReadyPlayers());
                game.start();
                game.sendScore();
                roundsPlayed++;
            }

            synchronized (this) { playing = false; }
        }
        terminateThisLobby();
    }

    public synchronized void play(String playerId) {
        if (hostId.equals(playerId)) {
            players.get(hostId).setReady(true);
            this.playing = true;
        }
    }

    public void removePlayer(String playerId){
        players.remove(playerId);
        if (playerId.equals(hostId)) {
            setNewHost();
        }
    }

    //New Players are added to PlayerList in Lobby, they will join in the next GameRound
    public void addPlayer(String playerId, String name, String color) {
        int player_count = players.size();
        if (player_count < maxPlayers) {
            Player pl = new Player(playerId, name, color);
            pl.setReady(true);
            players.put(pl.getId(), pl);
        }
    }

    public void updatePlayerConfig(String id, String name, String color, boolean ready) {
        Player pl = players.get(id);
        pl.setName(name);
        pl.setColor(color);
        pl.setReady(ready);
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

    public int getPlayersJoined() { return players.size(); }

    public synchronized boolean isPlaying() { return playing; }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public boolean isVisibleToPublic() {
        return visibleToPublic;
    }

    private GameMode getGameModeByName(String name, String lobbyId, Map<String, Player> players) {
        if (name.equals("classic")) { return new Classic(lobbyId, players, 4); }
        else if(name.equals("battleroyal")){return new BattleRoyal(lobbyId, players, 10000, 10000);}
        return null;
    }

    private HashMap<String, Player> getReadyPlayers() {
        HashMap<String, Player> readyPlayers = new HashMap<>();
        for(Map.Entry<String, Player>player : this.players.entrySet()){
            if(player.getValue().isReady()){
                readyPlayers.put(player.getKey(), player.getValue());
            }
        }
        return readyPlayers;
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
                one.put("playerName", player.getName());
                one.put("color", player.getColor());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            all.put(one);
        });
        players.put("players", all);
        players.put("host", (new JSONObject()).put("clientId", hostId));
        JSONObject lobbyConfig = new JSONObject();
        lobbyConfig.put("name", name);
        lobbyConfig.put("public", visibleToPublic);
        lobbyConfig.put("mode", mode);
        lobbyConfig.put("playersAllowed", maxPlayers);
        players.put("gameConfig", lobbyConfig);
        return players;
    }

    private void resetPlayers() {
        int gridInterval = game.getGridInterval();
        int x = 0;
        int y = gridInterval;
        for (Map.Entry<String, Player> player: players.entrySet()) {
            x += 4 * gridInterval;
            Player pl = player.getValue();
            pl.setPosx(x);
            pl.setPosy(y);
            pl.setDir(1);
            pl.getTurns().clear();
        }
    }

    private void terminateThisLobby() {
        LOGGER.info("Terminating Lobby: {}", id);
        GameManager.removeLobby(id);
        GameManager.getMessageForwarder().forwardMessage(new TerminateGameMessage(id));
    }

    private void setNewHost() {
        this.hostId = players.keySet().stream().findAny().orElse(null);
    }
}
