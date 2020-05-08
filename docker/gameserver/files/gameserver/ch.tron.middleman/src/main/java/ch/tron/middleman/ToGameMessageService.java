package ch.tron.middleman;
import ch.tron.middleman.messagedto.*;

public interface ToGameMessageService {
    void handle(InAppMessage msg);
}

