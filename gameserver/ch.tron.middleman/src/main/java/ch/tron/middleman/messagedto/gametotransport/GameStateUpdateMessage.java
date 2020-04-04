package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONObject;

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
