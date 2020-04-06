package ch.tron.game.controller;

import ch.tron.game.model.Game;
import ch.tron.game.model.GameRound;

import java.util.HashMap;
import java.util.Map;

public class GameController {

    private final Game game;
    private final Map<String, GameRoundController> roundControllers;

    public GameController(Game game) {
        this.roundControllers = new HashMap<>();

        this.game = game;
    }

    public void newGameRound(String groupId) {

        GameRound newRound = new GameRound(groupId);

        game.addRound(groupId, newRound);

        GameRoundController newRoundController = new GameRoundController(newRound);
        roundControllers.put(groupId, newRoundController);
    }

    public Game getGame() {
        return game;
    }

    public Map<String, GameRoundController> getRoundControllers() {
        return roundControllers;
    }

    public GameRoundController getGameRoundControllerById(String groupId) {
        return roundControllers.get(groupId);
    }
}
