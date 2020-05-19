package ch.tron.middleman.services;
import ch.tron.middleman.messagedto.*;

public interface ToGameMessageService {
    void handle(InAppMessage msg);
}

