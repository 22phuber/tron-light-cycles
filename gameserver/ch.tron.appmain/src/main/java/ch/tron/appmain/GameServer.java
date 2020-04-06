package ch.tron.appmain;

import ch.tron.game.GameManager;
import ch.tron.transport.TransportManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    public static void main(String[] args) {

        LOGGER.info("Starting up gameserver");

        GameManager gameManager = new GameManager();
        TransportManager transportManager = new TransportManager();
    }
}
