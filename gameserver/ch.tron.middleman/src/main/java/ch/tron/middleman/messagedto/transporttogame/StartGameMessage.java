package ch.tron.middleman.messagedto.transporttogame;

import ch.tron.middleman.messagedto.InAppMessage;

public class StartGameMessage extends InAppMessage {

    private final String host;

    public StartGameMessage(String host) { this.host = host; }

    public String getHost() {
        return host;
    }
}
