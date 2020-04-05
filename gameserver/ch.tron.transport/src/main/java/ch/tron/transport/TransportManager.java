package ch.tron.transport;

import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.gametotransport.GameStateUpdateMessage;
import ch.tron.middleman.messagehandler.ToGameMessageForwarder;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.messagedto.transporttogame.NewPlayerMessage;
import ch.tron.transport.webserverconfig.SocketInitializer;
import ch.tron.transport.websocketcontroller.WebSocketController;
import ch.tron.transport.websocketoutboundhandler.JsonOutboundHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.json.JSONObject;

public class TransportManager {

    // Reuse this instance in order to keep memory footprint small
    private static final JsonOutboundHandler out = new JsonOutboundHandler();

    private static final ToGameMessageForwarder MESSAGE_FORWARDER = new ToGameMessageForwarder();

    // This is temporary
    // Adding a 'default'-ChannelGroup
    // Connecting clients (new Channels) will be added to this group
    // Saving the UUID instantiated 'default'-ChannelGroup
    private static final String DEFAULT_CHANNEL_GROUP_ID = WebSocketController.newChannelGroup();

    public TransportManager() {
        SocketInitializer.init();

    }

    public static void manageNewChannel(Channel channel) {

        System.out.println("TransportManager: manageNewChannel is called");

        System.out.println("TransportManager: Id of DefaultChannelGroup is: " + DEFAULT_CHANNEL_GROUP_ID);

        // This is temporary
        // Automatically add new Channel to 'default'-ChannelGroup instantiated on top
        WebSocketController.addChannelToGroup(channel, DEFAULT_CHANNEL_GROUP_ID);


        MESSAGE_FORWARDER.forwardMessage(new NewPlayerMessage(channel.id().asLongText(), DEFAULT_CHANNEL_GROUP_ID));
    }

    // TODO: Implement handling of different msg
    public static void handleInAppIncomingMessage(InAppMessage msg) {

        //System.out.println("TransportManager: reflection works");

        if (msg instanceof GameStateUpdateMessage) {
            // String groupId = ((GameStateUpdateMessage) msg).getGroupId();

            // This is temporary
            ChannelGroup channelGroup = WebSocketController.getGroups().get(DEFAULT_CHANNEL_GROUP_ID);

            String update = ((GameStateUpdateMessage) msg).getUpdate().toString();

            out.sendUpdate(channelGroup, update);
        }
        else if (msg instanceof GameConfigMessage) {

            System.out.println("TransportManager: GameConfigMessage arrived");

            Channel channel = WebSocketController.getChannel(
                    "defaultId",
                    ((GameConfigMessage) msg).getPlayerId());
            int canvasWidth = ((GameConfigMessage) msg).getCanvas_width();
            int canvasHeight = ((GameConfigMessage) msg).getCanvas_height();

            JSONObject jo = new JSONObject();
            jo.put("subject", "canvas config");
            jo.put("width", canvasWidth);
            jo.put("height", canvasHeight);

            out.sendConfig(channel, jo.toString());
        }
    }

    public static ToGameMessageForwarder getMessageForwarder() {
        return MESSAGE_FORWARDER;
    }
}

