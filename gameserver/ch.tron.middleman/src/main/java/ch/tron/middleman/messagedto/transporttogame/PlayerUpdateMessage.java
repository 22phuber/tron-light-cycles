package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Is being sent from {@link ch.tron.transport} to {@link ch.tron.game}
 * using {@link ch.tron.middleman.messagehandler.ToGameMessageForwarder}
 * in order to communicate the updates from a specific game player (client) to
 * the game round the player is attending.
 */
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
