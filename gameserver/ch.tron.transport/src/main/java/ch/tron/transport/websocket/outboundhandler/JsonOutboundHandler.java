package ch.tron.transport.websocket.outboundhandler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.json.JSONObject;

/**
 * Manages forwarding of messages of type {@link JSONObject}
 * from game server to game player (client).
 */
public class JsonOutboundHandler {

    /**
     * Sends updated game state to game players (clients)
     * attending a given {@link ChannelGroup}.
     * @param group     The {@link ChannelGroup} to send the updates to.
     * @param update    The updated game state.
     */
    public void sendUpdate(ChannelGroup group, JSONObject update) {

        group.writeAndFlush(new TextWebSocketFrame(update.toString()));
    }

    /**
     * Sends the game configuration of the game to all players (clients)
     * attending a given {@link ChannelGroup}.
     * @param group     The {@link ChannelGroup} to send the config to.
     * @param config    The game configuration information.
     */
    public void sendConfig(ChannelGroup group, JSONObject config) {

        group.writeAndFlush(new TextWebSocketFrame(config.toString()));
    }
}
