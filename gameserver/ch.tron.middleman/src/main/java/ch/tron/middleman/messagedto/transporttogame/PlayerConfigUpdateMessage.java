package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Holds the updated configuration of a specific client.
 */
public class PlayerConfigUpdateMessage extends InAppMessage {

    private final String groupId;
    private final String clientId;
    private final String playerName;
    private final String playerColor;
    private final boolean ready;

    /**
     * Constructs a {@code PlayerConfigUpdateMessage} object.
     *
     * @param groupId       The id identifying the group of client
     *                      respectively the game the specific client
     *                      is part of
     * @param clientId      The id identifying the client respectively
     *                      the player who updated his configuration
     * @param playerName    The potentially updated name of the player
     * @param playerColor   The potentially updated color of the player
     * @param ready         The potentially updated ready status of the
     *                      player
     */
    public PlayerConfigUpdateMessage(String groupId,
                                     String clientId,
                                     String playerName,
                                     String playerColor,
                                     boolean ready) {
        this.groupId = groupId;
        this.clientId = clientId;
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.ready = ready;
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
     * Returns the id identifying the player who sends this
     * configuration update.
     *
     * @return  The player id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Return the name of the player who sends this configuration
     * update.
     *
     * @return  The players name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the color of the player who sends this
     * configuration update.
     *
     * @return  The players color
     */
    public String getPlayerColor() {
        return playerColor;
    }

    /**
     * Return the ready status of the player who sends this
     * configuration update.
     *
     * @return  The ready status
     */
    public boolean isReady() {
        return ready;
    }
}
