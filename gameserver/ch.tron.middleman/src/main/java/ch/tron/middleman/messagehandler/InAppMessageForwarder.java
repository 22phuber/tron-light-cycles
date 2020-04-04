package ch.tron.middleman.messagehandler;

import ch.tron.middleman.messagedto.InAppMessage;

public interface InAppMessageForwarder {
    void forwardMessage(InAppMessage msg);
}
