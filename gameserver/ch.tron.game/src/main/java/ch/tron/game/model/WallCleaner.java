package ch.tron.game.model;

import java.util.LinkedList;
import java.util.Queue;

public class WallCleaner {
    private Queue<Position> positions = new LinkedList<>();
    private int wallSize;

    public WallCleaner(int wallSize) {
        this.wallSize = wallSize;
    }

    public void addPosition(Position position){
        this.positions.add(position);
    }

    public Position getPosition(){
        if(positions.size() == wallSize){
            return positions.remove();
        }
        return null;
    }

    public Position peekPosition(){
        if(positions.size() >= wallSize){
            return positions.peek();
        }
        return null;
    }
}
