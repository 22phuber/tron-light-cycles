package ch.tron.middleman.services;

import ch.tron.middleman.messagedto.InAppMessage;

public interface ToTransportMessageService {
    void handle(InAppMessage msg);
}

