package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

public class PlayerUpdateMessage extends InAppMessage {

    private final String playerId;
    private final String key;

    public PlayerUpdateMessage(String playerId, String key) {
        this.playerId = playerId;
        this.key = key;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getKey() {
        return key;
    }
}
