package ch.tron.appmain;

import ch.tron.game.GameManager;
import ch.tron.transport.TransportManager;

public class GameServer {

    public static void main(String[] args) {

        GameManager gameManager = new GameManager();
        TransportManager transportManager = new TransportManager();
    }
}
