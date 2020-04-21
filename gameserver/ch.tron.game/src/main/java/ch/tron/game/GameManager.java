package ch.tron.game;

import ch.tron.game.config.CanvasConfig;
import ch.tron.game.controller.Lobby;
import ch.tron.game.model.Player;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.transporttogame.NewLobby;
import ch.tron.middleman.messagedto.transporttogame.NewPlayerMessage;
import ch.tron.middleman.messagedto.transporttogame.PlayerUpdateMessage;
import ch.tron.middleman.messagehandler.ToTransportMessageForwarder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Connects {@link ch.tron.game} to {@code ch.tron.middleman}.
 * Manages forwarding of {@link InAppMessage}.
 */
public class GameManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    private static final ToTransportMessageForwarder MESSAGE_FORWARDER = new ToTransportMessageForwarder();

    private Map<String, Player> players = new HashMap<>();

    private static List<Lobby> lobbies = new ArrayList<>();

    public static ToTransportMessageForwarder getMessageForwarder() { return MESSAGE_FORWARDER; }

    /**
     * Handles incoming {@link InAppMessage} from {@code ch.tron.middleman}.
     *
     * @param msg   Message of type {@link InAppMessage}.
     */
    public static void handleInAppIncomingMessage(InAppMessage msg) {
        
        if (msg instanceof NewPlayerMessage) {

            String groupId = ((NewPlayerMessage) msg).getGroupId();
            String playerId = ((NewPlayerMessage) msg).getPlayerId();

            MESSAGE_FORWARDER.forwardMessage(new GameConfigMessage(playerId, CanvasConfig.WIDTH.value(), CanvasConfig.HEIGHT.value()));

            lobbies.get(Integer.parseInt(groupId)).addPlayer(playerId);
        }
        else if (msg instanceof PlayerUpdateMessage) {

            String groupId = ((PlayerUpdateMessage) msg).getGroupId();
            String playerId = ((PlayerUpdateMessage) msg).getPlayerId();

            lobbies.get(Integer.parseInt(groupId)).updatePlayer(playerId, ((PlayerUpdateMessage) msg).getKey());
        }
        // TODO: M3: Implement
        else if (msg instanceof NewLobby) {

            String playerId = ((PlayerUpdateMessage) msg).getPlayerId();

            lobbies.add(new Lobby(lobbies.size(), playerId, "TEST"));
            new Thread(lobbies.get(lobbies.size()-1)).start();

        }
        else {
            LOGGER.info("Message type {} not supported", msg.getClass());
        }
    }
}
