package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONObject;

/**
 * Is being sent from {@link ch.tron.game} to {@link ch.tron.transport} using
 * {@link ch.tron.middleman.messagehandler.ToTransportMessageForwarder} in
 * order to communicate the updated game state of a specific game round
 * the attending game players (clients).
 */
public class GameStateUpdateMessage extends InAppMessage {

    private final String groupId;

    private JSONObject update;

    public GameStateUpdateMessage(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public JSONObject getUpdate() {
        return update;
    }

    public void setUpdate(JSONObject update) {
        this.update = update;
    }
}
