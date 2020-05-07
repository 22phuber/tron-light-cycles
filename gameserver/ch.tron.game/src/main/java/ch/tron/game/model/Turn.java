package ch.tron.game.model;

public class Turn {

    private final int newDirection;
    private int posx;
    private int posy;
    private boolean onHold = true;

    public Turn (int newDirection) {
        this.newDirection = newDirection;
    }

    public int getNewDirection() {
        return newDirection;
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

    public boolean isOnHold() {
        return onHold;
    }

    public void setOnHold(boolean onHold) {
        this.onHold = onHold;
    }
}
