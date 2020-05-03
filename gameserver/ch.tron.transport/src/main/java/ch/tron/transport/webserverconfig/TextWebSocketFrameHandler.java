package ch.tron.transport.webserverconfig;

import ch.tron.transport.TransportManager;
import ch.tron.transport.websocket.inboundhandler.JsonInboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Updates {@link io.netty.channel.ChannelPipeline} of a specific
 * client-server-connection on websocket handshake.
 * Handles incoming text-websocket-frames of a specific client.
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {

            logger.info("Handshake");

            ctx.pipeline().remove(HttpRequestHandler.class);

            TransportManager.manageNewChannel(ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        JSONObject jo;
        try {
            jo = new JSONObject(frame.text());
        } catch (JSONException e) {
            logger.info("Invalid JSON");
            return;
        }
        // TODO: Make JsonInboundHandler as object property and reuse
        new JsonInboundHandler(ctx.channel().id().asLongText(), jo);
    }
}
