package ch.tron.transport;

import ch.tron.middleman.messagedto.backAndForth.CurrentPublicGamesRequest;
import ch.tron.middleman.messagedto.gametotransport.*;
import ch.tron.middleman.messagedto.transporttogame.RemovePlayerMessage;
import ch.tron.middleman.messagehandler.ToGameMessageForwarder;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.transport.webserverconfig.SocketInitializer;
import ch.tron.transport.websocket.controller.WebSocketConnectionController;
import ch.tron.transport.websocket.outboundhandler.JsonOutboundHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.json.JSONArray;
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

        handleConnectionLoss(channel);

        WebSocketConnectionController.addChannelToLonelyGroup(channel);

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
                    WebSocketConnectionController.getLonelyChannel(((CurrentPublicGamesRequest) msg).getPlayerId()),
                    ((CurrentPublicGamesRequest) msg).getPublicGames());
        }
        else if (msg instanceof LobbyStateUpdateMessage) {
            out.sendJsonToChannelGroup(
                    WebSocketConnectionController.getChannelGroupById(((LobbyStateUpdateMessage) msg).getGroupId()),
                    ((LobbyStateUpdateMessage) msg).getUpdate()
            );
        }
        else if (msg instanceof GameConfigMessage) {
            JSONObject jo = new JSONObject()
                    .put("subject", "canvasConfig")
                    .put("width", ((GameConfigMessage) msg).getCanvas_width())
                    .put("height", ((GameConfigMessage) msg).getCanvas_height())
                    .put("lineThickness", ((GameConfigMessage) msg).getLineThickness());
            out.sendJsonToChannelGroup(WebSocketConnectionController.getChannelGroupById(((GameConfigMessage) msg).getGroupId()), jo);
        }
        else if (msg instanceof CountdownMessage) {
            JSONObject round = new JSONObject()
                    .put("current", ((CountdownMessage) msg).getCurrentRound())
                    .put("total", ((CountdownMessage) msg).getTotalRounds());
            JSONObject jo = new JSONObject()
                    .put("subject", "countdown")
                    .put("count", ((CountdownMessage) msg).getCount())
                    .put("round", round);
            out.sendJsonToChannelGroup(
                    WebSocketConnectionController.getChannelGroupById(((CountdownMessage) msg).getGroupId()),
                    jo
            );
        }
        else if (msg instanceof GameStateUpdateMessage) {
            JSONObject update = ((GameStateUpdateMessage) msg).getUpdate();
            if (((GameStateUpdateMessage) msg).isInitial()) {
                update.put("subject", "initialGameState");
            }
            out.sendJsonToChannelGroup(
                    WebSocketConnectionController.getChannelGroupById(((GameStateUpdateMessage) msg).getGroupId()),
                    update
            );
        }
        else if (msg instanceof DeathMessage) {
            String groupId = ((DeathMessage) msg).getGroupId();

            JSONObject jo = new JSONObject()
                    .put("subject", "playerDeath")
                    .put("gameId", groupId)
                    .put("playerName", ((DeathMessage) msg).getPlayerName())
                    .put("playerId", ((DeathMessage) msg).getPlayerId())
                    .put("posx", ((DeathMessage) msg).getPosx())
                    .put("posy", ((DeathMessage) msg).getPosy())
                    .put("turns", ((DeathMessage) msg).getTurns());
            out.sendJsonToChannelGroup(
                    WebSocketConnectionController.getChannelGroupById(groupId),
                    jo
            );
        }
        else if (msg instanceof ScoreMessage) {
            String groupId = ((ScoreMessage) msg).getGameId();

            JSONArray scores = new JSONArray();
            ((ScoreMessage) msg).getPlayerScores().forEach((id, score) -> {
                scores.put(new JSONObject()
                        .put("clientId", id)
                        .put("score", score));
            });
            JSONObject jo = new JSONObject()
                    .put("subject", "roundScores")
                    .put("gameId", groupId)
                    .put("playerScores", scores);
            out.sendJsonToChannelGroup(WebSocketConnectionController.getChannelGroupById(groupId), jo);
        }
        else if (msg instanceof TerminateGameMessage) {
            WebSocketConnectionController.deleteChannelGroup(((TerminateGameMessage) msg).getGroupId());
        }
        else {
            LOGGER.info("Message type {} not supported", msg.getClass());
        }
    }

    public static ToGameMessageForwarder getMessageForwarder() {
        return MESSAGE_FORWARDER;
    }

    public static JsonOutboundHandler getJsonOutboundHandler() { return out; }

    private static void handleConnectionLoss(Channel channel) {
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.addListener(channelFuture -> {
            String playerId = channel.id().asLongText();
            String groupId = WebSocketConnectionController.findGroupIdOfChannel(channel);
            LOGGER.info("Connection of client: {} attending game {} lost", playerId, groupId);
            if (groupId == null) {
                WebSocketConnectionController.removeChannelFromLonelyGroup(playerId);
            }
            else {
                getMessageForwarder().forwardMessage(new RemovePlayerMessage(groupId, playerId));
                WebSocketConnectionController.removeChannelFromGroup(channel, groupId);
            }
        });
    }
}
