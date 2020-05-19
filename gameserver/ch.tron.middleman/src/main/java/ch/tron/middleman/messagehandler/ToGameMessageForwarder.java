package ch.tron.middleman.messagehandler;

import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.services.ToGameMessageService;

/**
 * Implements a service to send {@link InAppMessage} from
 * {@link ch.tron.transport} to {@link ch.tron.game}.
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
