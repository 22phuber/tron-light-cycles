module ch.tron.transport {

    // JSON
    requires json;

    // Netty
    requires io.netty.codec;
    requires io.netty.codec.http;
    requires io.netty.common;
    requires io.netty.transport;

    // slf4j
    requires org.slf4j;

    // Tron
    requires ch.tron.middleman;


    exports ch.tron.transport;

    opens ch.tron.transport to ch.tron.middleman;
}