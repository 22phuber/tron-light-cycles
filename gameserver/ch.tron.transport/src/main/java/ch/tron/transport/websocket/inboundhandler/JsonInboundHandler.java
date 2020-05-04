package ch.tron.transport.websocket.inboundhandler;

import ch.tron.middleman.messagedto.backAndForth.CurrentPublicGamesRequest;
import ch.tron.middleman.messagedto.transporttogame.JoinLobbyMessage;
import ch.tron.middleman.messagedto.transporttogame.NewLobbyMessage;
import ch.tron.middleman.messagedto.transporttogame.PlayerUpdateMessage;
import ch.tron.middleman.messagedto.transporttogame.StartGameMessage;
import ch.tron.transport.TransportManager;
import ch.tron.transport.websocket.controller.WebSocketController;
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
                String gameId = WebSocketController.newChannelGroup();

                Channel channel = WebSocketController.getLonelyChannel(playerId);
                WebSocketController.addChannelToGroup(channel, gameId);
                WebSocketController.removeChannelFromLonelyGroup(playerId);

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

                channel = WebSocketController.getLonelyChannel(playerId);
                WebSocketController.addChannelToGroup(channel, gameId);
                WebSocketController.removeChannelFromLonelyGroup(playerId);

                TransportManager.getMessageForwarder().forwardMessage(new JoinLobbyMessage(
                        playerId,
                        jo.getString("playerName"),
                        jo.getString("playerColor"),
                        gameId
                ));
                break;
            case "startGame":
                TransportManager.getMessageForwarder().forwardMessage(new StartGameMessage(
                        jo.getString("gameId")
                ));
                break;
            case "updateDirection":
                TransportManager.getMessageForwarder().forwardMessage(new PlayerUpdateMessage(
                        jo.getString("gameId"),
                        playerId,
                        jo.getString("key")
                ));
                break;
            default: logger.info("Subject type {} not supported", subject);
        }
    }
}
