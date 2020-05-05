package ch.tron.transport;

import ch.tron.middleman.messagedto.backAndForth.CurrentPublicGamesRequest;
import ch.tron.middleman.messagedto.gametotransport.*;
import ch.tron.middleman.messagehandler.ToGameMessageForwarder;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.transport.webserverconfig.SocketInitializer;
import ch.tron.transport.websocket.controller.WebSocketController;
import ch.tron.transport.websocket.outboundhandler.JsonOutboundHandler;
import io.netty.channel.Channel;
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

        WebSocketController.addChannelToLonelyGroup(channel);

        JSONObject clientId = new JSONObject();
        clientId.put("subject", "clientId");
        clientId.put("id", channel.id().asLongText());
        out.sendJsonToChannel(channel, clientId);
    }

    /**
     * Handles incoming {@link InAppMessage} from {@code ch.tron.middleman}
     * forwarding them to the game player (client).
     *
     * @param msg   Message of type {@link InAppMessage}.
     */
    public static void handleInAppIncomingMessage(InAppMessage msg) {

        if (msg instanceof CurrentPublicGamesRequest) {
            out.sendJsonToChannel(
                    WebSocketController.getLonelyChannel(((CurrentPublicGamesRequest) msg).getPlayerId()),
                    ((CurrentPublicGamesRequest) msg).getPublicGames());
        }
        else if (msg instanceof LobbyStateUpdateMessage) {
            out.sendJsonToChannelGroup(
                    WebSocketController.getChannelGroup(((LobbyStateUpdateMessage) msg).getGroupId()),
                    ((LobbyStateUpdateMessage) msg).getUpdate()
            );
        }
        else if (msg instanceof GameConfigMessage) {
            JSONObject jo = new JSONObject()
                    .put("subject", "canvasConfig")
                    .put("width", ((GameConfigMessage) msg).getCanvas_width())
                    .put("height", ((GameConfigMessage) msg).getCanvas_height())
                    .put("lineThickness", ((GameConfigMessage) msg).getLineThickness());
            out.sendJsonToChannelGroup(WebSocketController.getChannelGroup(((GameConfigMessage) msg).getGroupId()), jo);
        }
        else if (msg instanceof SingleGameConfigMessage) {
            JSONObject jo = new JSONObject()
                    .put("subject", "canvasConfig")
                    .put("width", ((SingleGameConfigMessage) msg).getCanvas_width())
                    .put("height", ((SingleGameConfigMessage) msg).getCanvas_height())
                    .put("lineThickness", ((SingleGameConfigMessage) msg).getLineThickness());
            out.sendJsonToChannelGroup(WebSocketController.getChannelGroup(((SingleGameConfigMessage) msg).getPlayerId()), jo);
        }
        else if (msg instanceof CountdownMessage) {
            JSONObject jo = new JSONObject()
                    .put("subject", "countdown")
                    .put("count", ((CountdownMessage) msg).getCount());
            out.sendJsonToChannelGroup(
                    WebSocketController.getChannelGroup(((CountdownMessage) msg).getGroupId()),
                    jo
            );
        }
        else if (msg instanceof GameStateUpdateMessage) {
            JSONObject update = ((GameStateUpdateMessage) msg).getUpdate();
            if (((GameStateUpdateMessage) msg).isInitial()) {
                update.put("subject", "initialGameState");
            }
            out.sendJsonToChannelGroup(
                    WebSocketController.getChannelGroup(((GameStateUpdateMessage) msg).getGroupId()),
                    update
            );
        }
        else if (msg instanceof DeathMessage) {
            String groupId = ((DeathMessage) msg).getGroupId();

            JSONObject jo = new JSONObject()
                    .put("subject", "playerDeath")
                    .put("gameId", groupId)
                    .put("playerId", ((DeathMessage) msg).getPlayerId())
                    .put("posx", ((DeathMessage) msg).getPosx())
                    .put("posy", ((DeathMessage) msg).getPosy())
                    .put("turns", ((DeathMessage) msg).getTurns());
            out.sendJsonToChannelGroup(
                    WebSocketController.getChannelGroup(groupId),
                    jo
            );
        }
        else {
            LOGGER.info("Message type {} not supported", msg.getClass());
        }
    }

    public static ToGameMessageForwarder getMessageForwarder() {
        return MESSAGE_FORWARDER;
    }

    public static JsonOutboundHandler getJsonOutboundHandler() { return out; }
}

