package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Holds the configuration of a specific game. This information
 * contains it's field size and it's player size.
 */
public class GameConfigMessage extends InAppMessage {

    private final String groupId;
    private final int canvas_width;
    private final int canvas_height;
    private final int lineThickness;

    /**
     * ------------------------------------------------------------
     * Constructs a {@code GameConfigMessage} object.
     *
     * @param groupId       The id identifying the game
     * @param canvas_width  The width of the game field
     * @param canvas_height The height of the game field
     * @param lineThickness The size of the players light cycle
     */
    public GameConfigMessage(String groupId,
                             int canvas_width,
                             int canvas_height,
                             int lineThickness) {
        this.groupId = groupId;
        this.canvas_width = canvas_width;
        this.canvas_height = canvas_height;
        this.lineThickness = lineThickness;
    }

    /**
     * Returns the id identifying the game
     *
     * @return The game id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Return the games field width.
     *
     * @return The field width
     */
    public int getCanvas_width() {
        return canvas_width;
    }

    /**
     * Returns the games field height.
     *
     * @return The field height
     */
    public int getCanvas_height() {
        return canvas_height;
    }

    /**
     * Returns the players light cycle size.
     *
     * @return  The light cycle size
     */
    public int getLineThickness() {
        return lineThickness;
    }
}
