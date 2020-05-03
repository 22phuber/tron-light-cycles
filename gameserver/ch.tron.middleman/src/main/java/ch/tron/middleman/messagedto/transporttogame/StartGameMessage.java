package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class StartGameMessage extends InAppMessage {

    private final String groupId;

    public StartGameMessage(String groupId) { this.groupId = groupId; }

    public String getGroupId() {
        return groupId;
    }
}
