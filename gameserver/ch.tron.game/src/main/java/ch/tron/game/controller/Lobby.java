package ch.tron.game.controller;

import ch.tron.game.model.Game;
import ch.tron.game.model.GameRound;
import ch.tron.game.model.Player;

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

        //No Players in Lobby -> terminate Lobby
        while(players.size() > 0){

            int numberOfRounds = 5;

            while(!arePlayersReady()){

                //Host can change numberOfRounds
                //Players can set readyStatus
                //Players can leave....

            }

            //run set numbers of rounds. Default 5**
            while(numberOfRounds > 0) {

                gameRound = new GameRound("");
                gameRound.start();
                numberOfRounds--;

            }

        }
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

}
