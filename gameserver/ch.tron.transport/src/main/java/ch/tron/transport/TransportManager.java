package ch.tron.transport;

import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.gametotransport.GameStateUpdateMessage;
import ch.tron.middleman.messagedto.transporttogame.NewLobbyMessage;
import ch.tron.middleman.messagehandler.ToGameMessageForwarder;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.transport.webserverconfig.SocketInitializer;
import ch.tron.transport.websocket.controller.WebSocketController;
import ch.tron.transport.websocket.outboundhandler.JsonOutboundHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connects {@link ch.tron.transport} to {@code ch.tron.middleman} by
 * holding an instance of {@link ToGameMessageForwarder}. Manages
 * forwarding of {@link InAppMessage}.
 * Initiates set up of the web server and manages new client-server-connections.
 */
public class TransportManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportManager.class);

    // Reuse this instance in order to keep memory footprint small
    private static final JsonOutboundHandler out = new JsonOutboundHandler();

    private static final ToGameMessageForwarder MESSAGE_FORWARDER = new ToGameMessageForwarder();

    // This is temporary
    // Adding a 'default'-ChannelGroup
    // Connecting clients (new Channels) will be added to this group
    // Saving the UUID instantiated 'default'-ChannelGroup
    private static final String DEFAULT_CHANNEL_GROUP_ID = WebSocketController.newChannelGroup();

    public TransportManager() {

        LOGGER.info("Initialize transport");

        SocketInitializer.init();
    }

    /**
     * Manages a new client-server-connection and forwards the
     * game player information using {@link ToGameMessageForwarder} to
     * {@link ch.tron.game}.
     *
     * @param channel   The {@link Channel} representing a client-server-connection.
     */
    public static void manageNewChannel(Channel channel) {

        // This is temporary
        // Automatically add new Channel to 'default'-ChannelGroup instantiated on top
        WebSocketController.addChannelToGroup(channel, DEFAULT_CHANNEL_GROUP_ID);

        MESSAGE_FORWARDER.forwardMessage(new NewLobbyMessage(channel.id().asLongText(), DEFAULT_CHANNEL_GROUP_ID, new JSONObject()));
    }

    /**
     * Handles incoming {@link InAppMessage} from {@code ch.tron.middleman}
     * forwarding them to the game player (client).
     *
     * @param msg   Message of type {@link InAppMessage}.
     */
    public static void handleInAppIncomingMessage(InAppMessage msg) {

        if (msg instanceof GameStateUpdateMessage) {
            // String groupId = ((GameStateUpdateMessage) msg).getGroupId();

            // This is temporary
            ChannelGroup channelGroup = WebSocketController.getGroups().get(DEFAULT_CHANNEL_GROUP_ID);

            JSONObject update = ((GameStateUpdateMessage) msg).getUpdate();

            out.sendUpdate(channelGroup, update);
        }
        else if (msg instanceof GameConfigMessage) {

            Channel channel = WebSocketController.getChannel(
                    "defaultId",
                    ((GameConfigMessage) msg).getGroupId());
            int canvasWidth = ((GameConfigMessage) msg).getCanvas_width();
            int canvasHeight = ((GameConfigMessage) msg).getCanvas_height();

            JSONObject jo = new JSONObject();
            jo.put("subject", "canvas config");
            jo.put("width", canvasWidth);
            jo.put("height", canvasHeight);

            out.sendConfig(channel, jo);
        }
    }

    public static ToGameMessageForwarder getMessageForwarder() {
        return MESSAGE_FORWARDER;
    }
}

