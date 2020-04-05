package ch.tron.transport.webserverconfig;

import ch.tron.transport.TransportManager;
import ch.tron.transport.websocketinboundhandler.JsonInboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {

            System.out.println("Handshake");

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
            System.out.println("Invalid JSON");
            return;
        }
        new JsonInboundHandler(ctx.channel().id().asLongText(), jo);
    }
}
