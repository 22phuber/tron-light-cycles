package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class TerminateGameMessage extends InAppMessage {
    private final String groupId;
    private final String playerId;

    public TerminateGameMessage(String groupId, String playerId) {
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
