package ch.tron.game.controller;

import ch.tron.game.GameManager;
import ch.tron.game.model.*;
import ch.tron.middleman.messagedto.gametotransport.DeathMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class BattleRoyal extends GameMode{

    private Map<String, WallCleaner> wallCleaner = new HashMap<>();
    private Wall walls;
    private final int WALL_INTERVAL = 1000 * 10; //in milliseconds
    private long lastWallInterval;
    private int remainingWallSteps;

    public BattleRoyal(String lobbyId, Map<String, Player> players, int x, int y) {

        super(x, y, 4, 2, lobbyId, players);
        walls = new Wall(x, y);

        for(Map.Entry<String, Player> entry : players.entrySet()){
            wallCleaner.put(entry.getKey(), new WallCleaner(100));
        }
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
            gameStateUpdateMessage.setUpdate(render());
            GameManager.getMessageForwarder().forwardMessage(gameStateUpdateMessage);
            sleepForDelta(now);
        }
    }

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

            if (posx == walls.getWidth() || posx == fieldWitdh - walls.getWidth() || posy == walls.getHeight() || posy == fieldHeight - walls.getHeight() || field[posx][posy]) {
                die(player);
            }
            else {
                for (int i = 0; i < velocity; i++) {
                    for (int j = 0; j < velocity; j++) {
                        field[player.getPosx() + i][player.getPosy() + j] = true;
                    }
                }
                if(wallCleaner.get(player.getId()).peekPosition() != null) {
                    Position position = wallCleaner.get(player.getId()).peekPosition();
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

    public void moveWalls(){
        //move the outer walls closer to the center every "WALL_INTERVAL"
        if(remainingWallSteps > 0){

            walls.setWidth(walls.getWidth() - velocity);
            walls.setHeight(walls.getHeight() - velocity);
            remainingWallSteps--;
            if(remainingWallSteps == 0){
                lastWallInterval = System.currentTimeMillis();
            }
        }else if(System.currentTimeMillis() - lastWallInterval >= WALL_INTERVAL){
            //move walls closer to the center
            remainingWallSteps = 10;
        }
    }

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
                .put("x", fieldWitdh - walls.getHeight()).put("y", fieldHeight - walls.getHeight()));

        return state;
    }

    @Override
    public void die(Player pl) {
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
