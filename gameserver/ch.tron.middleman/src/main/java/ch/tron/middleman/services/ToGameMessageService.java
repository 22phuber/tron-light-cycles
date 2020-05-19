package ch.tron.middleman.services;
import ch.tron.middleman.messagedto.*;

/**
 * Service to send {@link InAppMessage} objects from
 * {@link ch.tron.transport} to {@link ch.tron.game}.
 */
public interface ToGameMessageService {

    /**
     * Passes a {@link InAppMessage} to {@link ch.tron.game}.
     *
     * @param msg   The message of type {@link InAppMessage}
     *              to be passed
     */
    void handle(InAppMessage msg);
}

