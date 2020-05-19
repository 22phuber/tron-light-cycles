import ch.tron.game.serviceProviders.ToGameMessageServiceImpl;
import ch.tron.middleman.services.ToGameMessageService;

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
    // needed to be exported for javadoc reasons
    exports ch.tron.game.controller;
    exports ch.tron.game.model;
    exports ch.tron.game.serviceProviders;

}