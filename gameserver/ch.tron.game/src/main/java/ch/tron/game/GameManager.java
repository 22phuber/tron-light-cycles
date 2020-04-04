package ch.tron.game;

import ch.tron.game.controller.GameController;
import ch.tron.game.controller.GameRoundController;
import ch.tron.game.model.Game;
import ch.tron.middleman.messagedto.transporttogame.NewGameRound;
import ch.tron.middleman.messagehandler.ToTransportMessageForwarder;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.messagedto.transporttogame.NewPlayerMessage;
import ch.tron.middleman.messagedto.gametotransport.PlayerUpdateMessage;

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
        System.out.println("GameManager: reflection works");

        if (msg instanceof NewPlayerMessage) {
            String playerId = ((NewPlayerMessage) msg).getPlayerId();
            getGameRoundController(playerId).addPlayer(playerId);
        }
        else if (msg instanceof PlayerUpdateMessage) {
            String playerId = ((PlayerUpdateMessage) msg).getPlayerId();
            getGameRoundController(playerId).updatePlayer(playerId, ((PlayerUpdateMessage) msg).getKey());
        }
        // TODO: M3: Implement
        else if (msg instanceof NewGameRound) {

        }
        else {
            System.out.println("GameManager: Message type not supported.");
        }
    }

    private static GameRoundController getGameRoundController(String playerId) {
        return DEFAULT_GAME_CONTROLLER.getGameRoundControllerById(playerId);
    }
}
