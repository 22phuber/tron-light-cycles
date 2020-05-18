package ch.tron.transport.websocket.inboundhandler;

import ch.tron.middleman.messagedto.backAndForth.CurrentPublicGamesRequest;
import ch.tron.middleman.messagedto.gametotransport.TerminateGameMessage;
import ch.tron.middleman.messagedto.transporttogame.*;
import ch.tron.transport.TransportManager;
import ch.tron.transport.websocket.controller.WebSocketConnectionController;
import io.netty.channel.Channel;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages forwarding of messages of type {@link JSONObject} from
 * a game player (client) to {@link ch.tron.game}
 */
public class JsonInboundHandler {

    private final Logger logger = LoggerFactory.getLogger(TransportManager.class);

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
            case "createGame":
                String gameId = WebSocketConnectionController.newChannelGroup();

                Channel channel = WebSocketConnectionController.getLonelyChannel(playerId);
                WebSocketConnectionController.addChannelToGroup(channel, gameId);
                WebSocketConnectionController.removeChannelFromLonelyGroup(playerId);

                JSONObject config = jo.getJSONObject("gameConfig");

                JSONObject host = jo.getJSONObject("host");

                TransportManager.getMessageForwarder().forwardMessage(new NewLobbyMessage(
                        gameId,
                        config.getString("name"),
                        playerId,
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

                channel = WebSocketConnectionController.getLonelyChannel(playerId);
                WebSocketConnectionController.addChannelToGroup(channel, gameId);
                WebSocketConnectionController.removeChannelFromLonelyGroup(playerId);

                TransportManager.getMessageForwarder().forwardMessage(new JoinLobbyMessage(
                        playerId,
                        jo.getString("playerName"),
                        jo.getString("playerColor"),
                        gameId
                ));
                break;
            case "playerConfigUpdate":
                TransportManager.getMessageForwarder().forwardMessage(new PlayerConfigUpdateMessage(
                        jo.getString("gameId"),
                        playerId,
                        jo.getString("playerName"),
                        jo.getString("playerColor"),
                        jo.getBoolean("ready")
                ));
                break;
            case "startGame":
                TransportManager.getMessageForwarder().forwardMessage(new StartGameMessage(
                        jo.getString("gameId"),
                        playerId
                ));
                break;
            case "updateDirection":
                TransportManager.getMessageForwarder().forwardMessage(new PlayerUpdateMessage(
                        jo.getString("gameId"),
                        playerId,
                        jo.getString("key")
                ));
                break;
            case "leaveGame":
                gameId = jo.getString("gameId");
                channel = WebSocketConnectionController.getChannelFromGroup(gameId, playerId);
                WebSocketConnectionController.addChannelToLonelyGroup(channel);
                WebSocketConnectionController.removeChannelFromGroup(channel, gameId);

                TransportManager.getMessageForwarder().forwardMessage(new RemovePlayerMessage(
                        jo.getString("gameId"),
                        playerId
                ));
                break;
            default: logger.info("Subject type {} not supported", subject);
        }
    }
}
