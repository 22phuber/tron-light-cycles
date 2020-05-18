package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

public class TerminateGameMessage extends InAppMessage {
    private final String groupId;

    public TerminateGameMessage(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }
}
