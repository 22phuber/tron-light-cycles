package ch.tron.transport.serviceProviders;

import ch.tron.middleman.services.ToTransportMessageService;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.transport.TransportManager;

/**
 * Service provider for {@link ToTransportMessageService}.
 */
public class ToTransportMessageServiceImpl implements ToTransportMessageService {

    /**
     * Initializes a newly created
     * {@code ToTransportMessageServiceImpl} object.
     */
    public ToTransportMessageServiceImpl() {}

    /**
     * Has to be called in order to pass a {@link InAppMessage} to
     * a class of {@link ch.tron.transport}.
     *
     * @param msg   The message of type {@link InAppMessage}
     *              to be passed
     */
    public void handle(InAppMessage msg) {
        TransportManager.handleInAppIncomingMessage(msg);
    }
}