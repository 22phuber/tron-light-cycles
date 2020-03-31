package transport.handler;

import game.model.GameRound;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class JSONOutboundHandler {

    public void sendUpdate(GameRound round) {

        String update = round.playersJSON().toString();

        round.getChannelGroup().writeAndFlush(new TextWebSocketFrame(update));

    }
}
