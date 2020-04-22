package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONObject;

/**
 * Is being sent from {@link ch.tron.transport} to {@link ch.tron.game}
 * using {@link ch.tron.middleman.messagehandler.ToGameMessageForwarder}
 * in order to create a new game round of a specific game.
 */
public class NewLobbyMessage extends InAppMessage {

    private final String playerId;
    private final String  groupId;
    private final JSONObject config;

    public NewLobbyMessage(String playerId, String groupId, JSONObject config) {
        this.playerId = playerId;
        this.groupId = groupId;
        this.config = config;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getGroupId() {
        return groupId;
    }

    public JSONObject getConfig() { return config; }
}
