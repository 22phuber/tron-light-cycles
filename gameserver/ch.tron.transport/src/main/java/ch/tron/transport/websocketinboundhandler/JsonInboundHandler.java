package ch.tron.transport.websocketinboundhandler;

import ch.tron.middleman.messagedto.gametotransport.PlayerUpdateMessage;
import ch.tron.transport.TransportManager;
import org.json.JSONObject;

public class JsonInboundHandler {

    private final String playerId;

    public JsonInboundHandler(String playerId, JSONObject jo) {
        this.playerId = playerId;

        handleMsg(jo);
    }

    private void handleMsg(JSONObject jo) {

        if (jo.getString("subject").equals("update dir")) {

            String key = jo.getString("key");

            TransportManager.getMessageForwarder().forwardMessage(new PlayerUpdateMessage(playerId, key));
        }
    }
}
