package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class PlayerUpdateMessage extends InAppMessage {

    private final String groupId;
    private final String playerId;
    private final String key;

    public PlayerUpdateMessage(String groupId, String playerId, String key) {
        this.groupId = groupId;
        this.playerId = playerId;
        this.key = key;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getKey() {
        return key;
    }
}
