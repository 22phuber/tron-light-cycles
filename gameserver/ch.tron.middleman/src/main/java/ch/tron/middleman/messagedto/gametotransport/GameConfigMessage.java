package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Is being sent from {@link ch.tron.game} to {@link ch.tron.transport} using
 * {@link ch.tron.middleman.messagehandler.ToTransportMessageForwarder} in
 * order to communicate the configuration of a specific game/game round to a
 * newly attending game player (client).
 */
public class GameConfigMessage extends InAppMessage {

    private final String groupId;
    private final int canvas_width;
    private final int canvas_height;

    public GameConfigMessage(String groupId, int canvas_width, int canvas_height) {
        this.groupId = groupId;
        this.canvas_width = canvas_width;
        this.canvas_height = canvas_height;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getCanvas_width() {
        return canvas_width;
    }

    public int getCanvas_height() {
        return canvas_height;
    }
}
