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

    private boolean initial = false;

    public GameStateUpdateMessage(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public JSONObject getUpdate() {
        return update;
    }

    /**
     * Sets the current game state
     * @param update    The current game state as {@link JSONObject}
     *                  with the following pattern:
     *                  {
     *                      "subject": "gameState",
     *                      "gameId": "theGameId",
     *                      "players": [
     *                          {
     *                              "clientId": "theClientId",
     *                              "posx": int,
     *                              "posy": int,
     *                              "dir": int,
     *                              "color": "rgb(int,int,int)"
     *                          },
     *                          ...
     *                      ]
     *                  }
     */
    public void setUpdate(JSONObject update) {
        this.update = update;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }
}
