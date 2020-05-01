package ch.tron.game.model;

public class Turn {

    private final int posx;
    private final int posy;
    private final int newDirection;

    public Turn (int posx, int posy, int newDirection) {
        this.posx = posx;
        this.posy = posy;
        this.newDirection = newDirection;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public int getNewDirection() {
        return newDirection;
    }
}
