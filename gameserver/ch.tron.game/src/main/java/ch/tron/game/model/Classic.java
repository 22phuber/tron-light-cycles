package ch.tron.game.model;

import ch.tron.game.GameManager;
import ch.tron.middleman.messagedto.gametotransport.CountdownMessage;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Classic extends GameMode{

    public Classic(String lobbyId, Map<String,Player> players){
        super(400,400,5, lobbyId, players);
    }

    @Override
    public void start() {

        GameManager.getMessageForwarder().forwardMessage(new GameConfigMessage(lobbyId, x, y, lineThickness));

        gameStateUpdateMessage.setInitial(true);
        gameStateUpdateMessage.setUpdate(playersJSON());
        GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);
        gameStateUpdateMessage.setInitial(false);

        int count = 3;
        final CountdownMessage countdownMsg = new CountdownMessage(lobbyId);
        while (count != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(800);
                countdownMsg.setCount(count--);
                GameManager.getMessageForwarder().forwardMessage(countdownMsg);
            } catch (InterruptedException e) {
                logger.info(e.getMessage());
            }
        }

        logger.info("Game round started");

        long t_before = System.nanoTime();
        while (playersAlive.size() > 0) {
            long t_current = System.nanoTime();
            long t_delta = t_current - t_before;
            if (t_delta >= LOOP_INTERVAL) {
                t_before = t_current;
                playersAlive.values().forEach(player -> {
                    int posx = player.getPosx();
                    int posy = player.getPosy();
                    switch (player.getDir()) {
                        case 0:
                            if (posx != x) {
                                player.setPosx(posx + lineThickness);
                            }
                            break;
                        case 1:
                            if (posy != y) {
                                player.setPosy(posy + lineThickness);
                            }
                            break;
                        case 2:
                            if (posx != 0) {
                                player.setPosx(posx - lineThickness);
                            }
                            break;
                        case 3:
                            if (posy != 0) {
                                player.setPosy(posy - lineThickness);
                            }
                            break;
                    }
                    posx = player.getPosx();
                    posy = player.getPosy();
                    if (posx % x == 0 || posy % y == 0 || field[posx][posy]) {
                        die(player);
                    }
                    else {
                        for (int i = 0; i < lineThickness; i++) {
                            for (int j = 0; j < lineThickness; j++) {
                                field[player.getPosx() + i][player.getPosy() + j] = true;
                            }
                        }
                    }
                });
                gameStateUpdateMessage.setUpdate(playersJSON());
                GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);
            }
        }

    }
}
