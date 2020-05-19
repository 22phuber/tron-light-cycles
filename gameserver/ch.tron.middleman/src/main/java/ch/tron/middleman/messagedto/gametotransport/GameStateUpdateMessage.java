package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONObject;

/**
 * Holds the current game state.
 */
public class GameStateUpdateMessage extends InAppMessage {

    private final String groupId;

    private JSONObject update;

    private boolean initial = false;

    /**
     * Constructs a {@code GameStateUpdateMessage} object.
     *
     * @param groupId   The id identifying the game of which this
     *                  object will hold the updates
     */
    public GameStateUpdateMessage(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Returns the id identifying the game.
     *
     * @return  The game id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Returns the current game state as a {@link JSONObject}.
     *
     * @return  The game state
     */
    public JSONObject getUpdate() {
        return update;
    }

    /**
     * Updates the current game state.
     *
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

    /**
     * Tells whether this messages is a first or not.
     *
     * @return  {@code true} if it's the initial game state,
     *          {@code false} if it's the updated game state
     */
    public boolean isInitial() {
        return initial;
    }

    /**
     * Sets the initial value.
     *
     * @param initial   The initial value
     */
    public void setInitial(boolean initial) {
        this.initial = initial;
    }
}
