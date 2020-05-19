package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Holds the id of a client and the id of a game which the
 * specific client left.
 */
public class RemovePlayerMessage extends InAppMessage {
    private final String groupId;
    private final String clientId;

    /**
     * Constructs a {@code RemovePlayerMessage}.
     *
     * @param groupId   The id identifying the group of clients
     *                  respectively the game the client left
     * @param clientId  The id identifying the client respectively
     *                  the player that left the game
     */
    public RemovePlayerMessage(String groupId, String clientId) {
        this.groupId = groupId;
        this.clientId = clientId;
    }

    /**
     * Returns the id identifying the game which the client
     * leaves from.
     *
     * @return  The game id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Returns the id identifying the player who left the game.
     *
     * @return  The player id
     */
    public String getClientId() {
        return clientId;
    }
}
