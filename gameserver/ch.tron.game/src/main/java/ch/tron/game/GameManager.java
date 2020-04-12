package ch.tron.game;

import ch.tron.game.config.CanvasConfig;
import ch.tron.game.controller.GameController;
import ch.tron.game.model.Game;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.transporttogame.NewGameRound;
import ch.tron.middleman.messagedto.transporttogame.NewPlayerMessage;
import ch.tron.middleman.messagedto.transporttogame.PlayerUpdateMessage;
import ch.tron.middleman.messagehandler.ToTransportMessageForwarder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connects {@link ch.tron.game} to {@code ch.tron.middleman}.
 * Manages forwarding of {@link InAppMessage}.
 */
public class GameManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    private static final ToTransportMessageForwarder MESSAGE_FORWARDER = new ToTransportMessageForwarder();

    // This is temporary
    // Instantiate a Default-Game
    // Instantiate a Default-GameController
    private final static Game GAME;
    private final static GameController DEFAULT_GAME_CONTROLLER;

    static {

        GAME = new Game("defaultGame");
        DEFAULT_GAME_CONTROLLER = new GameController(GAME);

        // This is temporary
        // Add the Default-GameRound to the Default-Game
        // All players will be added to the Default-GameRound
        DEFAULT_GAME_CONTROLLER.newGameRound("defaultId");
    }

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

            DEFAULT_GAME_CONTROLLER.getGameRoundControllerById(groupId).addPlayer(playerId);
        }
        else if (msg instanceof PlayerUpdateMessage) {

            String groupId = ((PlayerUpdateMessage) msg).getGroupId();
            String playerId = ((PlayerUpdateMessage) msg).getPlayerId();

            DEFAULT_GAME_CONTROLLER.getGameRoundControllerById(groupId)
                    .updatePlayer(playerId, ((PlayerUpdateMessage) msg).getKey());
        }
        // TODO: M3: Implement
        else if (msg instanceof NewGameRound) {

        }
        else {
            LOGGER.info("Message type {} not supported", msg.getClass());
        }
    }
}
