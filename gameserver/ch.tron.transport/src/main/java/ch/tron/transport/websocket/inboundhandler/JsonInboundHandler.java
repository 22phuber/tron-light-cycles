package ch.tron.transport.websocket.inboundhandler;

import ch.tron.middleman.messagedto.backAndForth.CurrentPublicGamesRequest;
import ch.tron.middleman.messagedto.transporttogame.PlayerUpdateMessage;
import ch.tron.transport.TransportManager;
import org.json.JSONObject;

/**
 * Manages forwarding of messages of type {@link JSONObject} from
 * a game player (client) to {@link ch.tron.game}
 */
public class JsonInboundHandler {

    private final String playerId;

    public JsonInboundHandler(String playerId, JSONObject jo) {
        this.playerId = playerId;

        handleMsg(jo);
    }

    private void handleMsg(JSONObject jo) {

        String subject = jo.getString("subject");

        switch (subject) {
            case "currentPublicGames":
                TransportManager.getMessageForwarder().forwardMessage(new CurrentPublicGamesRequest(playerId));
                break;
        }

        if (jo.getString("subject").equals("update dir")) {

            String key = jo.getString("key");

            // default-groupId here is temporary
            // will come with the message from client in the future
            TransportManager.getMessageForwarder().forwardMessage(new PlayerUpdateMessage("defaultId", playerId, key));
        }
    }
}
