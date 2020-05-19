package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Holds the id identifying a game that was terminated.
 */
public class TerminateGameMessage extends InAppMessage {
    private final String groupId;

    /**
     * Constructs a {@code TerminateGameMessage} object.
     *
     * @param groupId   The id of the terminated game
     */
    public TerminateGameMessage(String groupId) {
        this.groupId = groupId;
    }

    /**
     * Returns the id of the terminated game.
     *
     * @return  The game id
     */
    public String getGroupId() {
        return groupId;
    }
}
