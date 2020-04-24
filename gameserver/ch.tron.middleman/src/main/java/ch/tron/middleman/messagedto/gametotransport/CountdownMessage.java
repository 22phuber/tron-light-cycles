package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

public class CountdownMessage extends InAppMessage {

    private final String groupId;
    private final int count;

    public CountdownMessage(String groupId, int count) {
        this.groupId = groupId;
        this.count = count;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getCount() {
        return count;
    }
}
