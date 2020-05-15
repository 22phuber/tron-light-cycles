package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class PlayerConfigUpdateMessage extends InAppMessage {

    private final String groupId;
    private final String playerId;
    private final String playerName;
    private final String playerColor;
    private final boolean ready;

    public PlayerConfigUpdateMessage(String groupId,
                                     String playerId,
                                     String playerName,
                                     String playerColor,
                                     boolean ready) {
        this.groupId = groupId;
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.ready = ready;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public boolean isReady() {
        return ready;
    }
}
