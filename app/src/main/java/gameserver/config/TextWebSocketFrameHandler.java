package gameserver.config;

import game.config.CanvasConfig;
import game.controller.Game;
import game.model.Player;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;


public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup channelGroup;
    private final Game game;

    public TextWebSocketFrameHandler(ChannelGroup group, Game game) {
        this.channelGroup = group;
        this.game = game;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {

            System.out.println("Handshake");

            ctx.pipeline().remove(HttpRequestHandler.class);
            channelGroup.add(ctx.channel());

            int player_count = game.playersMap().size();
            Color[] colors = game.getColors10();

            Player pl = new Player(
                    ctx.channel().id(),
                    50*player_count,
                    50*player_count,
                    1,
                    colors[player_count % colors.length]);

            game.addPlayer(ctx.channel().id(), pl);

            ctx.writeAndFlush(new TextWebSocketFrame(CanvasConfig.getCanvasConfig().toString()));

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        try {
            JSONObject jo = new JSONObject(frame.text());
            if (jo.getString("subject").equals("update dir")) {
                Player pl = game.playersMap().get(ctx.channel().id());
                int pl_dir = pl.getDir();
                String key = jo.getString("key");
                // HTML5 canvas coordinate system default setting
                // referenced by dir:
                //            (dir = 3)
                //                |
                //                |
                // (dir = 2) ----------- x (dir = 0)
                //                |
                //                |
                //                y
                //            (dir = 1)
                switch (key) {
                    case "ArrowLeft":
                        pl_dir = (pl_dir == 0) ? 3 : --pl_dir;
                        break;
                    case "ArrowRight":
                        pl_dir = (pl_dir == 3) ? 0 : ++pl_dir;
                        break;
                }
                pl.setDir(pl_dir);
            }
        } catch (JSONException e) {
            System.out.println("Invalid JSON");
        }
    }

}
