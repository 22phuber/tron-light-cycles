package ch.tron.middleman.messagedto.gametotransport;


import ch.tron.middleman.messagedto.InAppMessage;

import java.util.Map;

public class ScoreMessage extends InAppMessage {
    private final String gameId;
    private final Map<String, Integer> playerScores;

    public ScoreMessage(String gameId, Map<String, Integer> playerScores) {
        this.gameId = gameId;
        this.playerScores = playerScores;
    }

    public String getGameId() {
        return gameId;
    }

    public Map<String, Integer> getPlayerScores() {
        return playerScores;
    }
}
