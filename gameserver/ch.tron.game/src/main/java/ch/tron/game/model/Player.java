package ch.tron.game.model;

import java.awt.Color;

public class Player {

    private final String id;
    private int posx;
    private int posy;
    private int dir;
    private Color color;

    public Player(String  id, int posx, int posy, int dir, Color color) {
        this.id = id;
        this.posx = posx;
        this.posy = posy;
        this.dir = dir;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}