package ch.tron.game;

import ch.tron.game.controller.Lobby;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.messagedto.transporttogame.LobbyConfigMessage;
import ch.tron.middleman.messagedto.transporttogame.NewLobbyMessage;
import ch.tron.middleman.messagedto.transporttogame.JoinLobbyMessage;
import ch.tron.middleman.messagedto.transporttogame.PlayerUpdateMessage;
import ch.tron.middleman.messagehandler.ToTransportMessageForwarder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Connects {@link ch.tron.game} to {@code ch.tron.middleman}.
 * Manages forwarding of {@link InAppMessage}.
 */
public class GameManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    private static final ToTransportMessageForwarder MESSAGE_FORWARDER = new ToTransportMessageForwarder();

    private static Map<String,Lobby> lobbies = new HashMap<>();

    public static ToTransportMessageForwarder getMessageForwarder() { return MESSAGE_FORWARDER; }

    /**
     * Handles incoming {@link InAppMessage} from {@code ch.tron.middleman}.
     *
     * @param msg   Message of type {@link InAppMessage}.
     */
    public static void handleInAppIncomingMessage(InAppMessage msg) {
        
        if (msg instanceof JoinLobbyMessage) {

            String groupId = msg.getGroupId();
            String playerId = ((JoinLobbyMessage) msg).getPlayerId();

            lobbies.get(groupId).addPlayer(playerId);
        }
        else if (msg instanceof PlayerUpdateMessage) {

            String groupId = msg.getGroupId();
            String playerId = ((PlayerUpdateMessage) msg).getPlayerId();

            lobbies.get(groupId).updatePlayer(playerId, ((PlayerUpdateMessage) msg).getKey());
        }
        // TODO: M3: Implement
        else if (msg instanceof NewLobbyMessage) {

            String groupId = msg.getGroupId();
            String playerId = ((NewLobbyMessage) msg).getPlayerId();

            lobbies.put(groupId, new Lobby(groupId, playerId, "TEST"));
            new Thread(lobbies.get(groupId)).start();

        }else if(msg instanceof LobbyConfigMessage){

            String groupId = msg.getGroupId();
            String playerId = ((LobbyConfigMessage) msg).getPlayerId();
            JSONObject config = ((LobbyConfigMessage) msg).getConfig();

            lobbies.get(groupId).setConfig(playerId, config);

        }
        else {
            LOGGER.info("Message type {} not supported", msg.getClass());
        }
    }
}
