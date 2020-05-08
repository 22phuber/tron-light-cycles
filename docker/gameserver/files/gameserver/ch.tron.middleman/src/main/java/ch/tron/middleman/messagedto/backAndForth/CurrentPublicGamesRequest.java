package ch.tron.middleman.messagedto.backAndForth;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONObject;

import java.util.HashSet;

/**
 * Is being sent from {@link ch.tron.transport} to {@link ch.tron.game} using
 * {@link ch.tron.middleman.messagehandler.ToGameMessageForwarder} in
 * order to request the currently open public games. The object is expected
 * to be sent back from {@link ch.tron.game} to {@link ch.tron.transport} using
 * {@link ch.tron.middleman.messagehandler.ToTransportMessageForwarder} having
 * set the publicGames property.
 */
public class CurrentPublicGamesRequest extends InAppMessage {

    private final String playerId;

    private JSONObject publicGames = null;

    public CurrentPublicGamesRequest(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public JSONObject getPublicGames() {
        return publicGames;
    }

    /**
     * Sets the publicGames property.
     *
     * @param publicGames   All currently publicly open games and their
     *                      associated properties as a {@link JSONObject}
     *                      as following:
     *                      { "subject": "currentPublicGames",
     *                        "games": [
     *                            { "id": "theGameId",
     *                              "name": "theGameName",
     *                              "playersJoined": int,
     *                              "playersAllowed": int,
     *                              "mode": "gameMode"
     *                            },
     *                            {...}, ...
     *                        ]
     *                      }
     */
    public void setPublicGames(JSONObject publicGames) {
        this.publicGames = publicGames;
    }
}
