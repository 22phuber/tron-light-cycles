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

    /**
     *
     * @param playerId  The hosts id.
     * @param groupId   The id of the game to be created.
     * @param config    The configuration the host has chosen for this new
     *                  game as a {@link JSONObject} with the following
     *                  pattern:
     *                  {
     *                      "name": "theChosenGameName",
     *                      "visibility": "theChosenVisibility",
     *                      "mode": "theChosenGameMode",
     *                      "playersAllowed": int },
     *                      "hostName": "theClientsName"
     *                  }
     */
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
