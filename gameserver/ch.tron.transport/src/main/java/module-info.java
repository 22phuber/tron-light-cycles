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


    exports ch.tron.transport;

    opens ch.tron.transport to ch.tron.middleman;
}