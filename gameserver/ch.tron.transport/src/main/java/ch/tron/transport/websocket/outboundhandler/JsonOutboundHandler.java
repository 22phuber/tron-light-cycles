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
     * Sends a JsonString to a client.
     *
     * @param channel   The {@link Channel} representing the connection
     *                  to the client.
     * @param jo        The {@link JSONObject} holding the information
     *                  to be sent.
     */
    public void sendJsonToChannel(Channel channel, JSONObject jo) {
        channel.writeAndFlush(new TextWebSocketFrame(jo.toString()));
    }

    /**
     * Sends a JsonString to a group of clients.
     *
     * @param group     The {@link ChannelGroup} to send to.
     * @param jo        The {@link JSONObject} holding the information
     *                  to be sent.
     */
    public void sendJsonToChannelGroup(ChannelGroup group, JSONObject jo) {
        group.writeAndFlush(new TextWebSocketFrame(jo.toString()));
    }
}
