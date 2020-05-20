package ch.tron.transport.websocket.inboundhandler;

import ch.tron.middleman.messagedto.backAndForth.CurrentPublicGamesRequest;
import ch.tron.middleman.messagedto.transporttogame.*;
import ch.tron.transport.TransportManager;
import ch.tron.transport.websocket.controller.WebSocketConnectionController;
import io.netty.channel.Channel;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages forwarding of messages of type {@link JSONObject}
 * from a game player (client) to {@link ch.tron.game}
 */
public class JsonInboundHandler {

    private final Logger logger = LoggerFactory.getLogger(TransportManager.class);

    private final String clientId;

    /**
     * Initializes a newly created {@code JsonOutboundHandler}
     * object and sends the given message to the client matching
     * the given id.
     *
     * @param clientId  The id representing the client-server
     *                  connection
     * @param jo        The message of type {@link JSONObject} to
     *                  be passed to the client by this
     *                  {@code JsonOutboundHandler} object
     */
    public JsonInboundHandler(String clientId, JSONObject jo) {
        this.clientId = clientId;

        handleMsg(jo);
    }

    private void handleMsg(JSONObject jo) {

        String subject = jo.getString("subject");

        switch (subject) {
            case "currentPublicGames":
                TransportManager.getMessageForwarder().forwardMessage(new CurrentPublicGamesRequest(clientId));
                break;
            case "createGame":
                String gameId = WebSocketConnectionController.newChannelGroup();

                Channel channel = WebSocketConnectionController.getLonelyChannel(clientId);
                WebSocketConnectionController.addChannelToGroup(channel, gameId);
                WebSocketConnectionController.removeChannelFromLonelyGroup(clientId);

                JSONObject config = jo.getJSONObject("gameConfig");

                JSONObject host = jo.getJSONObject("host");

                TransportManager.getMessageForwarder().forwardMessage(new NewLobbyMessage(
                        gameId,
                        config.getString("name"),
                        clientId,
                        host.getString("playerName"),
                        host.getString("color"),
                        config.getString("mode"),
                        config.getInt("playersAllowed"),
                        config.getBoolean("public")
                ));
                TransportManager.getJsonOutboundHandler().sendJsonToChannel(
                        channel,
                        new JSONObject().put("subject", "createGame").put("gameId", gameId)
                );
                break;
            case "joinGame":
                gameId = jo.getString("gameId");

                channel = WebSocketConnectionController.getLonelyChannel(clientId);
                WebSocketConnectionController.addChannelToGroup(channel, gameId);
                WebSocketConnectionController.removeChannelFromLonelyGroup(clientId);

                TransportManager.getMessageForwarder().forwardMessage(new JoinLobbyMessage(
                        clientId,
                        jo.getString("playerName"),
                        jo.getString("playerColor"),
                        gameId
                ));
                break;
            case "playerConfigUpdate":
                TransportManager.getMessageForwarder().forwardMessage(new PlayerConfigUpdateMessage(
                        jo.getString("gameId"),
                        clientId,
                        jo.getString("playerName"),
                        jo.getString("playerColor"),
                        jo.getBoolean("ready")
                ));
                break;
            case "startGame":
                TransportManager.getMessageForwarder().forwardMessage(new StartGameMessage(
                        jo.getString("gameId"),
                        clientId
                ));
                break;
            case "updateDirection":
                TransportManager.getMessageForwarder().forwardMessage(new PlayerUpdateMessage(
                        jo.getString("gameId"),
                        clientId,
                        jo.getString("key")
                ));
                break;
            case "leaveGame":
                gameId = jo.getString("gameId");
                channel = WebSocketConnectionController.getChannelFromGroup(gameId, clientId);
                WebSocketConnectionController.addChannelToLonelyGroup(channel);
                WebSocketConnectionController.removeChannelFromGroup(channel, gameId);

                TransportManager.getMessageForwarder().forwardMessage(new RemovePlayerMessage(
                        jo.getString("gameId"),
                        clientId
                ));
                break;
            default: logger.info("Subject type {} not supported", subject);
        }
    }
}
