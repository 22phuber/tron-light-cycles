module ch.tron.transport {

    // Netty
    requires io.netty.codec;
    requires io.netty.codec.http;
    requires io.netty.common;
    requires io.netty.transport;

    // JSON
    requires json;

    // Tron
    requires ch.tron.middleman;
    requires java.desktop;

    exports ch.tron.transport;


    // TODO: Answer:
    // This is not needed here. Why?
    opens ch.tron.transport to ch.tron.middleman;
}