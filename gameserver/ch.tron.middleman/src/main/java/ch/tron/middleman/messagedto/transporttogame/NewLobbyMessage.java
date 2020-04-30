package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Is being sent from {@link ch.tron.transport} to {@link ch.tron.game}
 * using {@link ch.tron.middleman.messagehandler.ToGameMessageForwarder}
 * in order to create a new game round of a specific game.
 */
public class NewLobbyMessage extends InAppMessage {

    private final String hostId;
    private final String hostName;
    private final String groupId;
    private final String groupName;
    private final String mode;
    private final int playersAllowed;
    private final boolean visibleToPublic;

    public NewLobbyMessage(String hostId,
                           String hostName,
                           String groupId,
                           String groupName,
                           String mode,
                           int playersAllowed,
                           boolean visibleToPublic) {
        this.hostId = hostId;
        this.hostName = hostName;
        this.groupId = groupId;
        this.groupName = groupName;
        this.mode = mode;
        this.playersAllowed = playersAllowed;
        this.visibleToPublic = visibleToPublic;
    }

    public String getHostId() {
        return hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getMode() {
        return mode;
    }

    public int getPlayersAllowed() {
        return playersAllowed;
    }

    public boolean isVisibleToPublic() {
        return visibleToPublic;
    }
}
