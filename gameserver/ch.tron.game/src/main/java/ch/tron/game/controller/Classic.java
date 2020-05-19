package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.model.Player;
import ch.tron.game.model.Turn;

import java.util.Map;
import java.util.function.BiPredicate;

/**
 * Implements the Classic Tron game mode.
 * It extends the GameMode Class.
 */
public class Classic extends GameMode{

    public Classic(String lobbyId, Map<String, Player> players, int gridInterval){

        super(gridInterval * 200,
                gridInterval * 200,
                gridInterval,
                gridInterval / 2,
                lobbyId,
                players);
    }

    /**
     * Main Game Loop
     *
     * Runs as long as players are alive.
     * It continously sends update to the clients.
     * The loop is capped at 60 FPS.
     */
    @Override
    public void start() {

        logger.info("Classic game started");
        initialize();
        countdown();

        while (playersAlive.size() > 0) {
            long now = System.nanoTime();

            move();

            gameStateUpdateMessage.setUpdate(render());
            GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);

            sleepForDelta(now);
        }

    }

    /**
     * Moves every player towards the direction it is headed.
     * Checks if a player dies.
     */
    @Override
    public void move() {
        playersAlive.values().forEach(player -> {

            int posx = player.getPosx();
            int posy = player.getPosy();

            BiPredicate<Integer, Integer> posOnGrid = (x, y) -> x % gridInterval == 0 && y % gridInterval == 0;

            Turn turn;
            if (player.getTurns().size() != 0) {
                turn = player.getTurns().getLast();
                if (turn.isOnHold() && posOnGrid.test(posx, posy)) {
                    executeTurn(player, turn, posx, posy);
                }
            }

            switch (player.getDir()) {
                case 0:
                    if (posx != fieldWitdh) {
                        player.setPosx(posx + velocity);
                    }
                    break;
                case 1:
                    if (posy != fieldHeight) {
                        player.setPosy(posy + velocity);
                    }
                    break;
                case 2:
                    if (posx != 0) {
                        player.setPosx(posx - velocity);
                    }
                    break;
                case 3:
                    if (posy != 0) {
                        player.setPosy(posy - velocity);
                    }
                    break;
            }

            posx = player.getPosx();
            posy = player.getPosy();

            if (posx % fieldWitdh == 0 || posy % fieldHeight == 0 || field[posx][posy]) {
                die(player);
            }
            else {
                for (int i = 0; i < velocity; i++) {
                    for (int j = 0; j < velocity; j++) {
                        field[player.getPosx() + i][player.getPosy() + j] = true;
                    }
                }
            }
        });
    }
}
