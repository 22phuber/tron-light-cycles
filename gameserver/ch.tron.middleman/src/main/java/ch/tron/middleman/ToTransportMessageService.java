package ch.tron.middleman;

import ch.tron.middleman.messagedto.InAppMessage;

public interface ToTransportMessageService {
    void handle(InAppMessage msg);
}

