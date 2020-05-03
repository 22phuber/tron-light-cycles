package ch.tron.game.model;

import ch.tron.game.GameManager;
import ch.tron.middleman.messagedto.gametotransport.CountdownMessage;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BattleRoyal extends GameMode{

    public BattleRoyal(String lobbyId, Map<String, Player> players) {
        super(10000, 10000, 5, lobbyId, players);
    }

    @Override
    public void start() {

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

        logger.info("BattleRoyal game started");

        while (playersAlive.size() > 0) {
            now = System.nanoTime();





            delta = System.nanoTime() - now;
            if(delta < LOOP_INTERVAL){
                try {
                    Thread.sleep((LOOP_INTERVAL - delta) / 1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
