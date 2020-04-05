package transport.handler;

import transport.GameServer;
import io.netty.channel.ChannelId;
import org.json.JSONObject;

public class JSONInboundHandler {

    private final ChannelId id;

    public JSONInboundHandler(ChannelId id, JSONObject jo) {
        this.id = id;
        
        analyseMsg(jo);
    }

    private void analyseMsg(JSONObject jo) {

        if (jo.getString("subject").equals("update dir")) {

            GameServer.GAME_CONTROLLER.updatePlayer(jo, id);

        }
    }
}
