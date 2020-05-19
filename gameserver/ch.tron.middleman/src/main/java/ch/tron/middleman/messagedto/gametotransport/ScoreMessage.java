package ch.tron.middleman.messagedto.gametotransport;


import ch.tron.middleman.messagedto.InAppMessage;

import java.util.Map;

/**
 * Holds the scores achieved in a game round of any player
 * joining the specific game.
 */
public class ScoreMessage extends InAppMessage {
    private final String gameId;
    private final Map<String, Integer> playerScores;

    /**
     * Constructs a {@code ScoreMessage} object.
     *
     * @param gameId        The id identifying a specific game
     * @param playerScores  A {@link Map} holding the id's of the
     *                      players joining the game as keys and
     *                      their scores as values
     */
    public ScoreMessage(String gameId, Map<String, Integer> playerScores) {
        this.gameId = gameId;
        this.playerScores = playerScores;
    }

    /**
     * Returns the id identifying the the game.
     *
     * @return  The game id
     */
    public String getGameId() {
        return gameId;
    }

    /**
     * Returns a {@link Map} containing every players id as key and
     * its scores achieved in a specific game round as value.
     *
     * @return  The scores of every player joining the game
     */
    public Map<String, Integer> getPlayerScores() {
        return playerScores;
    }
}
