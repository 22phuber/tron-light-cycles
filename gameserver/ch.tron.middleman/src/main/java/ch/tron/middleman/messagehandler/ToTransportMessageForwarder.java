package ch.tron.middleman.messagehandler;

import ch.tron.middleman.services.ToTransportMessageService;
import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Locates and loads {@link ToTransportMessageService}.
 */
public class ToTransportMessageForwarder implements InAppMessageForwarder {

    @Override
    public void forwardMessage(InAppMessage msg) {
        for (ToTransportMessageService ms : java.util.ServiceLoader.load(ToTransportMessageService.class)) {
            ms.handle(msg);
            break;
        }
    }

}
