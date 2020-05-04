package ch.tron.game.model;

import ch.tron.game.GameManager;
import ch.tron.middleman.messagedto.gametotransport.CountdownMessage;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BattleRoyal extends GameMode{

    private final int WALL_INTERVAL = 1000 * 10; //in milliseconds
    private long lastWallInterval;

    public BattleRoyal(String lobbyId, Map<String, Player> players) {
        super(10000, 10000, 5, lobbyId, players);
    }

    @Override
    public void start() {

        logger.info("BattleRoyal game started");
        initialize();
        countdown();

        lastWallInterval = System.currentTimeMillis();

        while (playersAlive.size() > 0) {
            now = System.nanoTime();

            move();
            moveWalls();
            render();

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

    @Override
    public void move() {
        //gameplay logic comes in here
    }

    public void moveWalls(){
        //move the outer walls closer to the center every "WALL_INTERVAL"
        if(System.currentTimeMillis() - lastWallInterval >= WALL_INTERVAL){
            //move walls closer to the center
            lastWallInterval = System.currentTimeMillis();
        }
    }

}
