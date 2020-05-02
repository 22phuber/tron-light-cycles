package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Is being sent from {@link ch.tron.transport} to {@link ch.tron.game}
 * using {@link ch.tron.middleman.messagehandler.ToGameMessageForwarder}
 * in order to add a new game player to a specific game round.
 */
public class JoinLobbyMessage extends InAppMessage {

    private final String playerId;
    private final String color;
    private final String  groupId;

    public JoinLobbyMessage(String playerId, String color, String groupId) {
        this.playerId = playerId;
        this.color = color;
        this.groupId = groupId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getColor() { return color; }

    public String getGroupId() {
        return groupId;
    }
}
