package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Holds the countdown to a starting game round of a specific
 * game.
 */
public class CountdownMessage extends InAppMessage {

    private final String groupId;
    private final int currentRound;
    private final int totalRounds;
    private int count;

    /**
     * Constructs a {@code CountdownMessage} object.
     *
     * @param groupId       The id identifying the game
     * @param currentRound  The # of the current round
     * @param totalRounds   The # of rounds in total
     */
    public CountdownMessage(String groupId, int currentRound, int totalRounds) {
        this.groupId = groupId;
        this.currentRound = currentRound;
        this.totalRounds = totalRounds;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getCount() {
        return count;
    }

    /**
     * Updates the countdown to a new round.
     *
     * @param count {@link Integer} either 3, 2, or 1
     */
    public void setCount(int count) {
        this.count = count;
    }
}
