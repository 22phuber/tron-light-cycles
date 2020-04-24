package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

public class DeathMessage extends InAppMessage {

    private final String groupId;
    private final String playerId;
    private final int posx;
    private final int posy;

    public DeathMessage(String groupId, String playerId, int posx, int posy) {
        this.groupId = groupId;
        this.playerId = playerId;
        this.posx = posx;
        this.posy = posy;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }
}
