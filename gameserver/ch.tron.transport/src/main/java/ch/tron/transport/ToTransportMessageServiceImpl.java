package ch.tron.transport;

import ch.tron.middleman.ToGameMessageService;
import ch.tron.middleman.ToTransportMessageService;
import ch.tron.middleman.messagedto.InAppMessage;

public class ToTransportMessageServiceImpl implements ToTransportMessageService {
    public void handle(InAppMessage msg) {
        TransportManager.handleInAppIncomingMessage(msg);
    }
}