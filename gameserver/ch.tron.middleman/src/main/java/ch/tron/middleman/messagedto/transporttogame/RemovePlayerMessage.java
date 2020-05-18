package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class RemovePlayerMessage extends InAppMessage {
    private final String groupId;
    private final String playerId;

    public RemovePlayerMessage(String groupId, String playerId) {
        this.groupId = groupId;
        this.playerId = playerId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
