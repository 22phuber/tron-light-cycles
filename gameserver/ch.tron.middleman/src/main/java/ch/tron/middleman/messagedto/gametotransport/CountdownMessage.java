package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

public class CountdownMessage extends InAppMessage {

    private final String groupId;
    private final int currentRound;
    private final int totalRounds;
    private int count;

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

    public void setCount(int count) {
        this.count = count;
    }
}
