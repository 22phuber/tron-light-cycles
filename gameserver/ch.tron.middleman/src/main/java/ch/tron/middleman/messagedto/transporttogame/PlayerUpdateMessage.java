package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Holds the id of a client, the id of a game which the
 * specific client is part of and a {link String}
 * representation of the key the specific client pressed.
 */
public class PlayerUpdateMessage extends InAppMessage {

    private final String groupId;
    private final String clientId;
    private final String key;

    /**
     * Constructs a {@code PlayerUpdateMessage} object.
     *
     * @param groupId   The id identifying the group of clients
     *                  respectively the game the specific client
     *                  is part of
     * @param clientId  The id identifying the client respectively
     *                  the player who sends this key event
     * @param key       The key represented as a {@link String}
     *                  the client pressed
     */
    public PlayerUpdateMessage(String groupId, String clientId, String key) {
        this.groupId = groupId;
        this.clientId = clientId;
        this.key = key;
    }

    /**
     * Returns the id identifying the game the client is part of.
     *
     * @return  The game id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Returns the id identifying the player who sends this key
     * event.
     *
     * @return  The player id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Returns the key the client pressed.
     *
     * @return  The key
     */
    public String getKey() {
        return key;
    }
}
