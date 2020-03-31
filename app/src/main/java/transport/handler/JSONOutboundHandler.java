package transport.handler;

import game.config.CanvasConfig;
import game.model.GameRound;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


public class JSONOutboundHandler {

    public void sendUpdate(GameRound round) {

        String update = round.playersJSON().toString();

        round.getChannelGroup().writeAndFlush(new TextWebSocketFrame(update));

    }

    public void sendConfig(Channel channel) {
        channel.writeAndFlush(new TextWebSocketFrame(CanvasConfig.getCanvasConfig().toString()));
    }
}
