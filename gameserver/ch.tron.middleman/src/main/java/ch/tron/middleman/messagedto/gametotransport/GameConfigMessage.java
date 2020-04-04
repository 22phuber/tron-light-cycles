package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

public class GameConfigMessage extends InAppMessage {

    private final String playerId;
    private final int canvas_width;
    private final int canvas_height;

    public GameConfigMessage(String playerId, int canvas_width, int canvas_height) {
        this.playerId = playerId;
        this.canvas_width = canvas_width;
        this.canvas_height = canvas_height;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getCanvas_width() {
        return canvas_width;
    }

    public int getCanvas_height() {
        return canvas_height;
    }
}
