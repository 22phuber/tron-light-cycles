package ch.tron.game;

import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.ToGameMessageService;

public class ToGameMessageServiceImpl implements ToGameMessageService {
    public void handle(InAppMessage msg) {
        GameManager.handleInAppIncomingMessage(msg);
    }
}