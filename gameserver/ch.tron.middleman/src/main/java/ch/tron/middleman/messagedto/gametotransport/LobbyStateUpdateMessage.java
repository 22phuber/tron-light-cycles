package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONObject;

/**
 * Holds the current lobby state.
 */
public class LobbyStateUpdateMessage extends InAppMessage {

    private final String groupId;
    private JSONObject update;

    /**
     * ------------------------------------------------------------
     * Constructs a {@code LobbyStateUpdateMessage} object.
     *
     * @param groupId   The id identifying the lobby respectively
     *                  the game
     */
    public LobbyStateUpdateMessage(String groupId){
        this.groupId = groupId;
    }

    /**
     * Returns the id identifying the lobby/game.
     *
     * @return  The game id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Returns the current lobby state as {@link JSONObject}.
     *
     * @return  The updated lobby state
     */
    public JSONObject getUpdate() {
        return update;
    }

    /**
     * Updates the lobby state.
     *
     * @param update    The lobby state as {@link JSONObject}
     *                  with the following pattern:
     *                  {
     *                      "players": [
     *                          {
     *                              "clientId": "theClientId",
     *                              "name": "thePlayersName" ,
     *                              "ready": boolean
     *                          },
     *                          ...
     *                      ],
     *                      "host": { "clientId": "theHostClientsId" },
     *                      "gameConfig": {
     *                          "name": "theChosenGameName",
     *                          "public": boolean,
     *                          "mode": "theChosenGameMode",
     *                          "playersAllowed": int
     *                      }
     *                  }
     */
    public void setUpdate(JSONObject update) {
        this.update = update;
    }
}
