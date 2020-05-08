import ch.tron.game.ToGameMessageServiceImpl;
import ch.tron.middleman.ToGameMessageService;

module ch.tron.game {
    // Java
    requires java.desktop;

    // JSON
    requires json;

    // slf4j
    requires org.slf4j;

    // Tron
    requires ch.tron.middleman;

    provides ToGameMessageService with ToGameMessageServiceImpl;
    
    exports ch.tron.game;

}