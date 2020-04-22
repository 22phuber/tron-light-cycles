package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class GameStartMessage extends InAppMessage {

    private final String playerId;
    private final String  groupId;

    public GameStartMessage(String playerId, String groupId) {
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
