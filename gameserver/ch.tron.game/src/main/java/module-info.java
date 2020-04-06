module ch.tron.game {
    // Java
    requires java.desktop;

    // JSON
    requires json;

    // slf4j
    requires org.slf4j;

    // Tron
    requires ch.tron.middleman;


    exports ch.tron.game;

    opens ch.tron.game to ch.tron.middleman;

}