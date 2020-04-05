package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class NewGameRound extends InAppMessage {

    private String groupId;

    public NewGameRound(String groupId) {
        this.groupId = groupId;
    }
}
