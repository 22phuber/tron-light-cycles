package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.model.*;
import ch.tron.middleman.messagedto.gametotransport.DeathMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * Implements the BattleRoyal game mode.
 * It extends the GameMode Class.
 */

public class BattleRoyal extends GameMode{

    private Map<String, WallCleaner> wallCleaner = new HashMap<>();
    private Wall walls;
    private final int WALL_INTERVAL = 1000 * 10; //in milliseconds
    private long lastWallInterval;
    private int remainingWallSteps;

    /**
     * BattleRoyal Constructor
     *
     * @param lobbyId
     * @param players
     * @param x
     * @param y
     */
    public BattleRoyal(String lobbyId, Map<String, Player> players, int x, int y) {

        super(x, y, 4, 2, lobbyId, players);
        walls = new Wall(x, y, 0 , 0);

        for(Map.Entry<String, Player> entry : players.entrySet()){
            wallCleaner.put(entry.getKey(), new WallCleaner(100));
        }
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

        logger.info("BattleRoyal game started");
        initialize();
        countdown();

        lastWallInterval = System.currentTimeMillis();

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
     * Moves the outter walls a bit closer, when the timer is up.
     * Checks if a player dies.
     */
    @Override
    public void move() {
        //gameplay logic comes in here

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
                        wallCleaner.get(player.getId()).addPosition(new Position(posx + velocity, player.getPosy()));
                    }
                    break;
                case 1:
                    if (posy != fieldHeight) {
                        player.setPosy(posy + velocity);
                        wallCleaner.get(player.getId()).addPosition(new Position(player.getPosx(), posy + velocity));
                    }
                    break;
                case 2:
                    if (posx != 0) {
                        player.setPosx(posx - velocity);
                        wallCleaner.get(player.getId()).addPosition(new Position(posx - velocity, player.getPosy()));
                    }
                    break;
                case 3:
                    if (posy != 0) {
                        player.setPosy(posy - velocity);
                        wallCleaner.get(player.getId()).addPosition(new Position(player.getPosx(), posy - velocity));
                    }
                    break;
            }

            posx = player.getPosx();
            posy = player.getPosy();

            if (posx >= walls.getWidth() + walls.getX() || posy >= walls.getHeight() + walls.getY() || posx < walls.getX() || posy < walls.getY() ||field[posx][posy]) {
                die(player);
            }
            else {
                for (int i = 0; i < velocity; i++) {
                    for (int j = 0; j < velocity; j++) {
                        field[player.getPosx() + i][player.getPosy() + j] = true;
                    }
                }
                if(wallCleaner.get(player.getId()).peekPosition() != null) {
                    Position position = wallCleaner.get(player.getId()).getPosition();
                    for (int i = 0; i < velocity; i++) {
                        for (int j = 0; j < velocity; j++) {
                            field[position.getX() + i][position.getY() + j] = false;
                        }
                    }
                }
            }
        });

        moveWalls();
    }

    /**
     * Moves the walls a bit to the center after a given amount of time.
     */
    public void moveWalls(){
        //move the outer walls closer to the center every "WALL_INTERVAL"
        if(remainingWallSteps > 0){

            walls.setWidth(walls.getWidth() - velocity);
            walls.setHeight(walls.getHeight() - velocity);
            walls.setX(walls.getX() + velocity/2);
            walls.setY(walls.getY() + velocity/2);
            remainingWallSteps--;
            if(remainingWallSteps == 0){
                lastWallInterval = System.currentTimeMillis();
            }
        }else if(System.currentTimeMillis() - lastWallInterval >= WALL_INTERVAL){
            //move walls closer to the center
            remainingWallSteps = 100 * velocity;
        }
    }

    /**
     * Creates a JSONObject with the render info to send out to the clients.
     *
     * @return JSONObject with position info of every player and wall
     * @throws JSONException
     */
    @Override
    public JSONObject render() throws JSONException {
        JSONObject state = new JSONObject();
        state.put("subject", "gameState")
                .put("gameId", lobbyId);
        JSONArray all = new JSONArray();
        this.playersAlive.values().forEach(player -> {
            JSONObject one = new JSONObject();
            try {
                one.put("id", player.getId());
                one.put("posx", player.getPosx());
                one.put("posy", player.getPosy());
                one.put("dir", player.getDir());
                one.put("color", player.getColor());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            all.put(one);
        });
        state.put("players", all);

        state.put("walls", new JSONObject()
                .put("width", walls.getWidth()).put("height", walls.getHeight())
                .put("x", walls.getX()).put("y", walls.getY()));

        return state;
    }

    /**
     * This function gets called when a player dies in-game.
     * The player gets removed from the game and infos gets cleaned up.
     *
     * @param pl
     */
    @Override
    public void die(Player pl) {
        while(wallCleaner.get(pl.getId()).peekPosition() != null){
            Position position = wallCleaner.get(pl.getId()).getPosition();
            for (int i = 0; i < velocity; i++) {
                for (int j = 0; j < velocity; j++) {
                    field[position.getX() + i][position.getY() + j] = false;
                }
            }
        }
        playersAlive.remove(pl.getId());
        playersDead.add(pl.getId());
        JSONArray allTurns = new JSONArray();
        pl.getTurns().forEach(turn -> {
            JSONObject oneTurn = new JSONObject()
                    .put("posx", turn.getPosx())
                    .put("posy", turn.getPosy())
                    .put("newDirection", turn.getNewDirection());
            allTurns.put( oneTurn);
        });
        wallCleaner.remove(pl.getId());
        GameManager.getMessageForwarder().forwardMessage(new DeathMessage(
                lobbyId, pl.getId(), pl.getName(), pl.getPosx(), pl.getPosy(), allTurns
        ));
    }
}
