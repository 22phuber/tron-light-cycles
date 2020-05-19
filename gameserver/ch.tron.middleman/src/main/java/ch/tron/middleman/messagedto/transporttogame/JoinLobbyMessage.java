package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Holds the id identifying a game a client joins and the meta
 * information of the specific client.
 */
public class JoinLobbyMessage extends InAppMessage {

    private final String clientId;
    private final String playerName;
    private final String playerColor;
    private final String  groupId;

    /**
     * Constructs a {@code JoinLobbyMessage} object.
     *
     * @param clientId      The id identifying the client
     *                      respectively the player who joins
     *                      the game
     * @param playerName    The players name
     * @param playerColor   The players color
     * @param groupId       The id identifying the game the player
     *                      wants to join
     */
    public JoinLobbyMessage(String clientId, String playerName, String playerColor, String groupId) {
        this.clientId = clientId;
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.groupId = groupId;
    }

    /**
     * Returns the id identifying the joining client.
     *
     * @return  The players id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Returns the name of the joining client.
     *
     * @return  The players name
     */
    public String getPlayerName() { return playerName; }

    /**
     * Returns the color of the joining client.
     *
     * @return  The players color
     */
    public String getPlayerColor() { return playerColor; }

    /**
     * Returns the id identifying the game the client wants to
     * join.
     *
     * @return  The game id
     */
    public String getGroupId() {
        return groupId;
    }
}
