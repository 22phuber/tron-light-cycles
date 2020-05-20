package ch.tron.middleman.services;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Service to send {@link InAppMessage} objects from
 * {@link ch.tron.game} to {@link ch.tron.transport}.
 */
public interface ToTransportMessageService {

    /**
     * Passes a {@link InAppMessage} to
     * {@link ch.tron.transport}.
     *
     * @param msg   The message of type {@link InAppMessage}
     *              to be passed
     */
    void handle(InAppMessage msg);
}

