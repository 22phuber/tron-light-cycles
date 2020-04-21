package ch.tron.game.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a game lobby that holds all available types of tron {@link Game}.
 */
public class Lobby implements Runnable{

    private Game game;
    private GameRound gameRound;
    private Map<String, Player> players = new HashMap<>();

    public Lobby(Player host) {
        this.game = new Game("classic");
        this.players.put(host.getId(),host);
    }

    public void run(){
        
    }

}
