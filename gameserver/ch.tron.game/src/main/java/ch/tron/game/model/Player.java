package ch.tron.game.model;

import java.awt.*;
import java.util.LinkedList;

/**
 * Represents a game player.
 */
public class Player {

    private final String id;
    private final String name;
    private int posx;
    private int posy;
    private int dir;
    private final String color;
    private boolean ready = false;
    private final LinkedList turns = new LinkedList<Turn>();

    public Player(String  id, String name, int posx, int posy, int dir, String color) {
        this.id = id;
        this.name = name;
        this.posx = posx;
        this.posy = posy;
        this.dir = dir;
        this.color = color;
    }

    public void addTurn(int posx, int posy, int newDirection) {
        turns.addLast(new Turn(posx, posy, newDirection));
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

    public boolean isReady(){return this.ready;}

    public void setReady(boolean status){this.ready = status;}

    public boolean getReady(){return ready;}

    public LinkedList<Turn> getTurns() {
        return turns;
    }

    public String getName() {return name;}
}
