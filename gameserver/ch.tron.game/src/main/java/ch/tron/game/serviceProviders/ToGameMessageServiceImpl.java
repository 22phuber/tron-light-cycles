package ch.tron.game.serviceProviders;

import ch.tron.game.GameManager;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.services.ToGameMessageService;

public class ToGameMessageServiceImpl implements ToGameMessageService {

    @Override
    public void handle(InAppMessage msg) {
        GameManager.handleInAppIncomingMessage(msg);
    }
}