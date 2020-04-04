package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

public class GameConfigMessage extends InAppMessage {

    private final int canvas_width;
    private final int canvas_height;

    public GameConfigMessage(int canvas_width, int canvas_height) {
        this.canvas_width = canvas_width;
        this.canvas_height = canvas_height;
    }

    public int getCanvas_width() {
        return canvas_width;
    }

    public int getCanvas_height() {
        return canvas_height;
    }
}
