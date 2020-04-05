package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.config.CanvasConfig;
import ch.tron.game.config.GameColors;
import ch.tron.game.model.Player;
import ch.tron.game.model.GameRound;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;
import ch.tron.middleman.messagedto.gametotransport.GameStateUpdateMessage;

import java.awt.Color;

public class GameRoundController {

    private final GameRound round;

    // Reuse this instance in order to keep memory footprint small
    private final GameStateUpdateMessage gameStateUpdateMessage;

    private Thread gameLoop;
    private final double FPS = 60;
    private final double LOOP_INTERVAL = 1000000000 / FPS;

    public GameRoundController(GameRound round) {
        this.round = round;
        gameStateUpdateMessage = new GameStateUpdateMessage(round.getId());

        gameLoop = new Thread(this::run);
        gameLoop.start();
    }

    public void addPlayer(String playerId) {

        int player_count = round.playersMap().size();
        Color[] colors = GameColors.getColors10();

        Player pl = new Player(
                playerId,
                50*player_count,
                50*player_count,
                1,
                colors[player_count % colors.length]);

        GameManager.getMessageForwarder()
                .forwardMessage(new GameConfigMessage(playerId, CanvasConfig.WIDTH.value(), CanvasConfig.HEIGHT.value()));

        round.addPlayer(pl);
    }

    public void updatePlayer(String playerId, String key) {
        Player pl = round.playersMap().get(playerId);
        int pl_dir = pl.getDir();
        // HTML5 canvas coordinate system default setting
        // referenced by dir:
        //            (dir = 3)
        //                |
        //                |
        // (dir = 2) ----------- x (dir = 0)
        //                |
        //                |
        //                y
        //            (dir = 1)
        switch (key) {
            case "ArrowLeft":
                pl_dir = (pl_dir != 0)? 2 : 0;
                break;
            case "ArrowRight":
                pl_dir = (pl_dir != 2)? 0 : 2;
                break;
            case "ArrowUp":
                pl_dir = (pl_dir != 1)? 3 : 1;
                break;
            case "ArrowDown":
                pl_dir = (pl_dir != 3)? 1 : 3;
                break;
            default: // do nothing
        }
        pl.setDir(pl_dir);
    }

    private void run() {
        System.out.println("Game Loop running");
        long t_before = System.nanoTime();
        while (true) {
            long t_current = System.nanoTime();
            long t_delta = t_current - t_before;
            if (t_delta >= LOOP_INTERVAL) {
                t_before = t_current;
                round.playersMap().values().forEach(player -> {
                    switch (player.getDir()) {
                        case 0:
                            player.setPosx((player.getPosx() + 1) % CanvasConfig.WIDTH.value());
                            break;
                        case 1:
                            player.setPosy((player.getPosy() + 1) % CanvasConfig.HEIGHT.value());
                            break;
                        case 2:
                            int x = player.getPosx();
                            if (x == 0) {
                                player.setPosx(CanvasConfig.WIDTH.value());
                            } else {
                                player.setPosx(x - 1);
                            }
                            break;
                        case 3:
                            int y = player.getPosy();
                            if (y == 0) {
                                player.setPosy(CanvasConfig.HEIGHT.value());
                            } else {
                                player.setPosy(y - 1);
                            }
                            break;
                    }
                });
                gameStateUpdateMessage.setUpdate(round.playersJSON());
                GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);
            }
        }
    }
}