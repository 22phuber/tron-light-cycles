package ch.tron.game.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a game lobby that holds all available types of tron {@link Game}.
 */
public class Lobby implements Runnable{

    private Game game;
    private GameRound gameRound;
    private Map<String, Player> players = new HashMap<>();
    private int roundsPlayed = 0;

    public Lobby(Player host) {
        this.game = new Game("classic");
        this.players.put(host.getId(),host);
    }

    public void run(){

        int numberOfRounds = 5;

        //No Players in Lobby -> terminate Lobby
        while(players.size() > 0){

            while(!arePlayersReady()){

                //Host can change numberOfRounds
                //Players can set Readystatus

            }

            while(numberOfRounds > 0) {

                gameRound = new GameRound("");
                gameRound.start();
                numberOfRounds--;

            }

        }
    }

    private boolean arePlayersReady(){

        Iterator iterator = players.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry player = (Map.Entry)iterator.next();
            if(!player.getStatus()){
                return false;
            }
        }

        return true;
    }

}
