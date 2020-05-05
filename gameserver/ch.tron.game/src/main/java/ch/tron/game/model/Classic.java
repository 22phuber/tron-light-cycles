package ch.tron.game.model;

import ch.tron.game.GameManager;
import ch.tron.middleman.messagedto.gametotransport.GameConfigMessage;

import java.util.Map;

public class Classic extends GameMode{

    public Classic(String lobbyId, Map<String,Player> players){
        super(400,400,5, lobbyId, players);
    }

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

    @Override
    public void move() {
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
    }
}
