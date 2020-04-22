package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.config.CanvasConfig;
import ch.tron.game.config.GameColors;
import ch.tron.game.model.Game;
import ch.tron.game.model.GameRound;
import ch.tron.game.model.Player;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.gametotransport.GameStateUpdateMessage;
import ch.tron.middleman.messagedto.gametotransport.LobbyStateUpdateMessage;
import ch.tron.middleman.messagehandler.InAppMessageForwarder;
import ch.tron.middleman.messagehandler.ToTransportMessageForwarder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a game lobby that holds all available types of tron {@link Game}.
 */
public class Lobby implements Runnable{

    private LobbyStateUpdateMessage lobbyStateUpdateMessage;
    private Game game;
    private GameRound gameRound;
    private Map<String, Player> players = new HashMap<>();
    private int roundsPlayed = 0;
    private String id;
    private String name;
    private int numberOfRounds = 5;
    private Player host;

    public Lobby(String id, String host, String name) {
        this.id = id;
        this.game = new Game("classic");
        addPlayer(host);
        this.players.put(players.get(host).getId(),players.get(host));
        this.name = name;
        this.host = players.get(host);
        this.lobbyStateUpdateMessage = new LobbyStateUpdateMessage(id);
    }

    @Override
    public void run(){

        //No Players in Lobby -> terminate Lobby
        while(players.size() > 0){

            numberOfRounds = 5;

            while(!arePlayersReady()){

                lobbyStateUpdateMessage.setUpdate(getLobbyState());
                GameManager.getMessageForwarder().forwardMessage(lobbyStateUpdateMessage);

                //Host can change numberOfRounds
                //Host can set Game
                //Players can set readyStatus
                //Players can leave....

            }

            //MESSAGE_FORWARDER.forwardMessage(new GameConfigMessage(id, CanvasConfig.WIDTH.value(), CanvasConfig.HEIGHT.value()));

            //run set numbers of rounds. Default 5**
            while(numberOfRounds > 0) {

                gameRound = new GameRound(id, (HashMap) players);
                gameRound.start();
                numberOfRounds--;
                roundsPlayed++;

            }

        }
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

    private boolean arePlayersReady(){

        Iterator iterator = this.players.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry pair = (Map.Entry)iterator.next();
            Player player = (Player)pair.getValue();
            if(!player.isReady()){
                return false;
            }
        }

        return true;
    }

    //New Players are added to PlayerList in Lobby, they will join in the next GameRound
    public void addPlayer(String playerId) {

        int player_count = players.size();
        Color[] colors = GameColors.getColors10();

        Player pl = new Player(
                playerId,
                "username",
                50*player_count,
                50*player_count,
                1,
                colors[player_count % colors.length]);

        GameManager.getMessageForwarder()
                .forwardMessage(new GameConfigMessage(playerId, CanvasConfig.WIDTH.value(), CanvasConfig.HEIGHT.value()));

        players.put(pl.getId(), pl);
    }

    public void setGame(Game game){
        this.game = game;
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

}
