package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONObject;

public class LobbyConfigMessage extends InAppMessage {

    private final String playerId;
    private final String  groupId;
    private final JSONObject config;

    public LobbyConfigMessage(String playerId, String groupId, JSONObject config) {
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
