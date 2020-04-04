package ch.tron.transport.websocketcontroller;

import ch.tron.transport.websocketstate.WebSocketState;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.util.UUID;

public class WebSocketController {

    private static final WebSocketState state = new WebSocketState();

    public static UUID newChannelGroup() {
        return state.addChannelGroup(new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE));
    }

    public static void deleteChannelGroup(UUID id) {
        state.removeChannelGroup(id);
    }

    public static void addChannelToGroup(Channel channel, UUID groupId) {
        state.addChannelToGroup(channel, groupId);
    }

    public static void removeChannelFroumGroup(Channel channel, UUID groupId) {
        state.removeChannelFromGroup(channel, groupId);
    }
}
