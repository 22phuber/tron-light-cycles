package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Is being sent from {@link ch.tron.transport} to {@link ch.tron.game}
 * using {@link ch.tron.middleman.messagehandler.ToGameMessageForwarder}
 * in order to add a new game player to a specific game round.
 */
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
