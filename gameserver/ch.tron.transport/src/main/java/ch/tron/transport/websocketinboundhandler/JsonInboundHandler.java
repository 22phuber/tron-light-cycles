package ch.tron.transport.websocketinboundhandler;

import ch.tron.middleman.messagedto.transporttogame.PlayerUpdateMessage;
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

            System.out.println("JsonInboundHandler: handle update message: " + jo);

            String key = jo.getString("key");

            // default-groupId here is temporary
            // will come with the message from client in the future
            TransportManager.getMessageForwarder().forwardMessage(new PlayerUpdateMessage("defaultId", playerId, key));
        }
    }
}
