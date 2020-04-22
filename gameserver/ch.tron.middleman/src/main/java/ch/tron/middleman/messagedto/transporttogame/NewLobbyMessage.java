package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Is being sent from {@link ch.tron.transport} to {@link ch.tron.game}
 * using {@link ch.tron.middleman.messagehandler.ToGameMessageForwarder}
 * in order to create a new game round of a specific game.
 */
public class NewLobbyMessage extends InAppMessage {

    private String groupId;

    public NewLobbyMessage(String groupId) {
        this.groupId = groupId;
    }
}

