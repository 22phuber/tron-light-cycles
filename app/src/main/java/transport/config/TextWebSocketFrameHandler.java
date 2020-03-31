package transport.config;

import transport.GameServer;
import transport.handler.JSONInboundHandler;
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

            GameServer.GAME_CONTROLLER.addPlayer(ctx.channel());

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
        new JSONInboundHandler(ctx.channel().id(), jo);
    }
}
