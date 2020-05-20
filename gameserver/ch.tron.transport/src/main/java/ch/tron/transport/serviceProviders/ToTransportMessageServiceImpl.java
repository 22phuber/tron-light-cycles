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

    @Override
    public void handle(InAppMessage msg) {
        TransportManager.handleInAppIncomingMessage(msg);
    }
}