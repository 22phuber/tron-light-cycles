package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class LobbyConfigMessage extends InAppMessage {

    private final String playerId;
    private final String  groupId;

    public LobbyConfigMessage(String playerId, String groupId) {
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
