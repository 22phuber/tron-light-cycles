package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class NewPlayerMessage extends InAppMessage {

    private final String playerId;
    private final String  groupId;

    public NewPlayerMessage(String playerId, String groupId) {
        this.playerId = playerId;
        this.groupId = groupId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getGroupId() {
        return groupId;
    }
}
