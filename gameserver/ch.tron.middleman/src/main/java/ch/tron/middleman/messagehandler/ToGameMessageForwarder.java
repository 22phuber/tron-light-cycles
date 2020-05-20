package ch.tron.middleman.messagehandler;

import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.services.ToGameMessageService;

/**
 * Locates and loads {@link ToGameMessageService}.
 */
public class ToGameMessageForwarder implements InAppMessageForwarder {

    @Override
    public void forwardMessage(InAppMessage msg) {
        for (ToGameMessageService ms : java.util.ServiceLoader.load(ToGameMessageService.class)) {
            ms.handle(msg);
            break;
        }
    }
}
