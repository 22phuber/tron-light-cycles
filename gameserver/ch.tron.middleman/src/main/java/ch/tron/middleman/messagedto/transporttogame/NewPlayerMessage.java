package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

import java.util.UUID;

public class NewPlayerMessage extends InAppMessage {

    private final String playerId;
    private final UUID groupId;

    public NewPlayerMessage(String playerId, UUID groupId) {
        this.playerId = playerId;
        this.groupId = groupId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public UUID getGroupId() {
        return groupId;
    }
}
