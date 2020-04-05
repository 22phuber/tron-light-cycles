package ch.tron.transport.websocketcontroller;

import ch.tron.transport.websocketstate.WebSocketState;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.util.Map;

public class WebSocketController {

    private static final WebSocketState state = new WebSocketState();

    public static String newChannelGroup() {
        return state.addChannelGroup(new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE));
    }

    public static void deleteChannelGroup(String groupId) {
        state.removeChannelGroup(groupId);
    }

    public static void addChannelToGroup(Channel channel, String  groupId) {
        state.addChannelToGroup(channel, groupId);
    }

    public static void removeChannelFromGroup(Channel channel, String groupId) {
        state.removeChannelFromGroup(channel, groupId);
    }

    public static Map<String, ChannelGroup> getGroups() {
        return state.getGroups();
    }

    public static Channel getChannel(String groupId, String playerId) {
        return state.getChannel(groupId, playerId);
    }
}
