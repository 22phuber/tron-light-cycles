package ch.tron.transport.websocketoutboundhandler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class JsonOutboundHandler {

    public void sendUpdate(ChannelGroup group, String update) {

        group.writeAndFlush(new TextWebSocketFrame(update));
    }

    public void sendConfig(Channel channel, String config) {

        System.out.println("JsonOutboundHandler: send " + config + " to " + channel);

        channel.writeAndFlush(new TextWebSocketFrame(config));
    }
}
