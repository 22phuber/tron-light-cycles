package ch.tron.middleman.messagehandler;

import ch.tron.middleman.messagedto.InAppMessage;

/**
 * Defines functionality to send {@link InAppMessage}
 * from {@link ch.tron.game} to {@link ch.tron.transport} and
 * vise versa.
 */
public interface InAppMessageForwarder {

    /**
     * Forwards an {@link InAppMessage} to another module.
     *
     * @param msg   The message to be sent.
     */
    void forwardMessage(InAppMessage msg);
}
