package ch.tron.middleman.messagehandler;

import ch.tron.middleman.ToGameMessageService;
import ch.tron.middleman.ToTransportMessageService;
import ch.tron.middleman.messagedto.InAppMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implements a service to send {@link InAppMessage} from
 * {@link ch.tron.game} to {@link ch.tron.transport} using the
 * {@link java.lang.reflect} API.
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
