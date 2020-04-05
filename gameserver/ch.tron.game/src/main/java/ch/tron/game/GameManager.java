package ch.tron.game;

import ch.tron.game.config.CanvasConfig;
import ch.tron.game.controller.GameController;
import ch.tron.game.controller.GameRoundController;
import ch.tron.game.model.Game;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.transporttogame.NewGameRound;
import ch.tron.middleman.messagehandler.ToTransportMessageForwarder;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.messagedto.transporttogame.NewPlayerMessage;
import ch.tron.middleman.messagedto.transporttogame.PlayerUpdateMessage;

public class GameManager {

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

    public static <T extends InAppMessage> void handleInAppIncomingMessage(T msg) {

        if (msg instanceof NewPlayerMessage) {

            System.out.println("GameManager: NewPlayerMessage arrived");

            String groupId = ((NewPlayerMessage) msg).getGroupId();

            String playerId = ((NewPlayerMessage) msg).getPlayerId();

            MESSAGE_FORWARDER.forwardMessage(new GameConfigMessage(playerId, CanvasConfig.WIDTH.value(), CanvasConfig.HEIGHT.value()));

            DEFAULT_GAME_CONTROLLER.getGameRoundControllerById(groupId).addPlayer(playerId);
        }
        else if (msg instanceof PlayerUpdateMessage) {

            System.out.println("GameManager: PlayerUpdateMessage arrived");

            String groupId = ((PlayerUpdateMessage) msg).getGroupId();

            String playerId = ((PlayerUpdateMessage) msg).getPlayerId();

            getGameRoundController(groupId).updatePlayer(playerId, ((PlayerUpdateMessage) msg).getKey());
        }
        // TODO: M3: Implement
        else if (msg instanceof NewGameRound) {

        }
        else {
            System.out.println("GameManager: Message type not supported.");
        }
    }

    private static GameRoundController getGameRoundController(String groupId) {
        return DEFAULT_GAME_CONTROLLER.getGameRoundControllerById(groupId);
    }
}