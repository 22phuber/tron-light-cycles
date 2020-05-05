package ch.tron.game.model;

import java.util.Map;

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
            long now = System.nanoTime();
            move();
            render();
            sleepForDelta(now);
        }
    }

    @Override
    public void move() {
        //gameplay logic comes in here
        moveWalls();
    }

    public void moveWalls(){
        //move the outer walls closer to the center every "WALL_INTERVAL"
        if(System.currentTimeMillis() - lastWallInterval >= WALL_INTERVAL){
            //move walls closer to the center
            lastWallInterval = System.currentTimeMillis();
        }
    }

}
