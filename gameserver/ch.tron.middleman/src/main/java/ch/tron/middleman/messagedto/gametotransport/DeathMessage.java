package ch.tron.middleman.messagedto.gametotransport;

import ch.tron.middleman.messagedto.InAppMessage;
import org.json.JSONArray;

/**
 * Holds the position of a player at his death together with
 * meta information about his game play.
 */
public class DeathMessage extends InAppMessage {

    private final String groupId;
    private final String clientId;
    private final String playerName;
    private final int posx;
    private final int posy;
    private final JSONArray turns;

    /**
     * Constructs a {@code DeathMessage} object.
     *
     * @param groupId       The id identifying the game the dead
     *                      player is attending
     * @param clientId      The id identifying the dead player
     * @param playerName    The dead players name
     * @param posx          The x-coordinate at death
     * @param posy          The y-coordinate at death
     * @param turns         The coordinates of every turn made by
     *                      the dead player before he died
     */
    public DeathMessage(String groupId,
                        String clientId,
                        String playerName,
                        int posx,
                        int posy,
                        JSONArray turns) {
        this.groupId = groupId;
        this.clientId = clientId;
        this.playerName = playerName;
        this.posx = posx;
        this.posy = posy;
        this.turns = turns;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public JSONArray getTurns() {
        return turns;
    }
}
