package ch.tron.transport.webserverconfig;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * Defines the {@link ChannelPipeline} (the handlers being
 * passed by any message that is sent from any client to the
 * server) for any created client-server connection.
 */
public class WebSocketChannelInitializer extends ChannelInitializer<Channel> {

    /**
     * Initializes a {@code WebSocketChannelInitializer} object.
     */
    public WebSocketChannelInitializer() {}

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(64 * 1024))
                .addLast(new HttpRequestHandler("/ws"))
                .addLast(new WebSocketServerProtocolHandler("/ws"))
                .addLast(new TextWebSocketFrameHandler());
    }
}
