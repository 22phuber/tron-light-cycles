package ch.tron.middleman.messagedto.backAndForth;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONObject;

/**
 * Holds all games currently open for public.
 */
public class CurrentPublicGamesRequest extends InAppMessage {

    private final String clientId;

    private JSONObject publicGames = null;

    /**
     * Constructs a {@code CurrentPublicGamesRequest} object.
     *
     * @param clientId  The id identifying the client who requests
     *                  information about currently available games
     *                  that are open for public
     */
    public CurrentPublicGamesRequest(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public JSONObject getPublicGames() {
        return publicGames;
    }

    /**
     * Updates the publicGames property.
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
