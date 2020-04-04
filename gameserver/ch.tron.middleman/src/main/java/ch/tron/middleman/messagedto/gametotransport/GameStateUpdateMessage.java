package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

import java.util.Map;

// TODO: Send players seperately
// Send counter with them and assemble on arrival in package transport
// when counter reached number of Players for that specific GameRound
public class GameStateUpdateMessage extends InAppMessage {

    private final Map<String, ?> players;

    public GameStateUpdateMessage(String groupId, Map<String, ?> players) {
        this.players = players;
    }

    public Map<String, ?> getPlayers() {
        return players;
    }
}
