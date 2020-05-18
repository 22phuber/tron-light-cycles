package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONObject;

public class LobbyStateUpdateMessage extends InAppMessage {

    private final String groupId;
    private JSONObject update;

    public LobbyStateUpdateMessage(String groupId){
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public JSONObject getUpdate() {
        return update;
    }

    /**
     * Sets the lobby state.
     * @param update    The lobby state as {@link JSONObject}
     *                  with the following pattern:
     *                  {
     *                      players: [
     *                          {
     *                              "clientId": "theClientId",
     *                              "name": "thePlayersName" ,
     *                              "ready": boolean
     *                          },
     *                          ...
     *                      ]
     *                  }
     */
    public void setUpdate(JSONObject update) {
        this.update = update;
    }
}
