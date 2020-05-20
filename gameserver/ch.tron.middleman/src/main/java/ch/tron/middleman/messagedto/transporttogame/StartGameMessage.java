package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Holds the id of a client and the id of a game which the
 * specific client started.
 */
public class StartGameMessage extends InAppMessage {

    private final String groupId;
    private final String clientId;

    /**
     * Constructs a {@code StartGameMessage}
     *
     * @param groupId   The id identifying the group of clients
     *                  respectively the game that is to be started
     * @param clientId  The id identifying the client respectively
     *                  the player who's responsible for the game
     *                  to start
     */
    public StartGameMessage(String groupId, String clientId) {
        this.groupId = groupId;
        this.clientId = clientId;
    }

    /**
     * Returns the id identifying the game which is to be started.
     *
     * @return  The game id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Returns the id identifying the player responsible for
     * starting the game.
     *
     * @return  The player id
     */
    public String getClientId() {
        return clientId;
    }
}
