package ch.tron.transport;

import ch.tron.middleman.messagedto.gametotransport.GameStateUpdateMessage;
import ch.tron.middleman.messagehandler.ToGameMessageForwarder;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.messagedto.transporttogame.NewPlayerMessage;
import ch.tron.transport.webserverconfig.SocketInitializer;
import ch.tron.transport.websocketcontroller.WebSocketController;
import ch.tron.transport.websocketoutboundhandler.JsonOutboundHandler;
import io.netty.channel.Channel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Color;
import java.util.Map;
import java.util.UUID;

public class TransportManager {

    // Reuse this instance in order to keep memory footprint small
    private static final JsonOutboundHandler out = new JsonOutboundHandler();

    private static final ToGameMessageForwarder MESSAGE_FORWARDER = new ToGameMessageForwarder();

    // This is temporary
    // Adding a 'default'-ChannelGroup
    // Connecting clients (new Channels) will be added to this group
    // Saving the UUID instantiated 'default'-ChannelGroup
    private static final UUID CHANNEL_GROUP_ID = WebSocketController.newChannelGroup();

    public TransportManager() {
        SocketInitializer.init();

    }

    public static void manageNewChannel(Channel channel) {

        // This is temporary
        // Automatically add new Channel to 'default'-ChannelGroup instantiated on top
        WebSocketController.addChannelToGroup(channel, CHANNEL_GROUP_ID);

        MESSAGE_FORWARDER.forwardMessage(new NewPlayerMessage(channel.id().asLongText(), CHANNEL_GROUP_ID));
    }

    // TODO: Implement handling of different msg
    public static void handleInAppIncomingMessage(InAppMessage msg) {

        if (msg instanceof GameStateUpdateMessage) {

        }


        System.out.println("TransportManager: reflection works");
    }

    public static ToGameMessageForwarder getMessageForwarder() {
        return MESSAGE_FORWARDER;
    }

    // TODO: This doesn't work since package transports doesn't know package game
    // and therefor doesn't know Player
    private JSONObject playersMapToPlayersJsonString(Map<String, ?> players) throws JSONException {
        JSONObject update = new JSONObject();
        update.put("subject", "player update");
        JSONArray all = new JSONArray();
        players.values().forEach(player -> {
            JSONObject one = new JSONObject();
            try {
                one.put("id", player.getId());
                one.put("posx", player.getPosx());
                one.put("posy", player.getPosy());
                one.put("dir", player.getDir());
                one.put("color", awtColorToString(player.getColor()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            all.put(one);
        });
        update.put("players", all);
        return update;
    }

    private String awtColorToString(Color c) {
        StringBuilder sb = new StringBuilder();
        sb.append("rgb(")
                .append(c.getRed())
                .append(",")
                .append(c.getGreen())
                .append(",")
                .append(c.getBlue())
                .append(")");
        return sb.toString();
    }
}

