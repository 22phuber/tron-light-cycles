package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Holds the meta information of a newly created game by a
 * client and the configuration information of the specific
 * client who created it (the host).
 */
public class NewLobbyMessage extends InAppMessage {

    private final String groupId;
    private final String groupName;
    private final String clientId;
    private final String playerName;
    private final String playerColor;
    private final String gameMode;
    private final int playersAllowed;
    private final boolean visibleToPublic;

    /**
     * Constructs a {@code NewLobbyMessage} object.
     *
     * @param groupId           The id identifying the newly
     *                          created group of clients
     *                          respectively the newly created game
     * @param groupName         The name of the game the host chose
     * @param clientId          The id identifying the host
     * @param playerName        The name of the host
     * @param playerColor       The color of the host
     * @param gameMode          The mode of the game (classic or
     *                          battle royal)
     * @param playersAllowed    The number of players that will be
     *                          allowed to join the game
     * @param visibleToPublic   Defines whether the game is open
     *                          for everyone (public) or private
     */
    public NewLobbyMessage(
            String groupId,
            String groupName,
            String clientId,
            String playerName, String playerColor,
            String gameMode,
            int playersAllowed,
            boolean visibleToPublic) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.clientId = clientId;
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.gameMode = gameMode;
        this.playersAllowed = playersAllowed;
        this.visibleToPublic = visibleToPublic;
    }

    /**
     * Returns the id identifying the newly created game.
     *
     * @return  The game id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Returns the name of the newly created game.
     *
     * @return  The game name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Returns the id of the host.
     *
     * @return  The hosts id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Returns the name of the host.
     *
     * @return  The hosts name
     */
    public String getPlayerName() { return playerName; }

    /**
     * Returns the color of the host.
     *
     * @return  The hosts color
     */
    public String getPlayerColor() { return playerColor; }

    /**
     * Returns a {@link String} representation of the game mode
     * of the newly created game.
     *
     * @return  The game mode
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Returns the number of players allowed to join the game.
     *
     * @return  The number of players allowed
     */
    public int getPlayersAllowed() {
        return playersAllowed;
    }

    /**
     * Returns the public/private configuration of the game.
     *
     * @return  {@code true} if open to the public, {@code false}
     *          if private
     */
    public boolean isVisibleToPublic() {
        return visibleToPublic;
    }
}
