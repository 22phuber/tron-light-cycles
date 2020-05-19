package ch.tron.game.model;

import java.awt.*;
import java.util.LinkedList;

/**
 * Represents a game player.
 */
public class Player {

    private final String id;
    private String name;
    private String color;
    private boolean ready = false;
    private int posx;
    private int posy;
    private int dir;
    private final LinkedList turns = new LinkedList<Turn>();

    public Player(String  id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    /**
     * Creates a Turn out of the newDirection and returns it.
     *
     * @param newDirection
     * @return turn
     */
    public Turn addTurn(int newDirection) {
        Turn turn = new Turn(newDirection);
        turns.addLast(turn);
        return turn;
    }

    public String getId() {
        return id;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isReady(){return this.ready;}

    public void setReady(boolean status){this.ready = status;}

    public boolean getReady(){return ready;}

    public LinkedList<Turn> getTurns() {
        return turns;
    }

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }
}
