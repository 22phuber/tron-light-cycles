package ch.tron.transport.websocket.controller;

import ch.tron.transport.websocket.state.WebSocketState;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.util.Map;

/**
 * Controls the {@link WebSocketState}.
 */
public class WebSocketController {

    private static final WebSocketState state = new WebSocketState();

    /**
     * Creates a new {@link ChannelGroup} (group of client-server-connections)
     * @return  The id of the newly created {@link ChannelGroup} as a string
     */
    public static String newChannelGroup() {
        return state.addChannelGroup(new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE));
    }

    /**
     * Removes the {@link ChannelGroup} (group of client-server-connections) with the given id
     * @param groupId   The id of the {@link ChannelGroup} to be deleted
     */
    public static void deleteChannelGroup(String groupId) {
        state.removeChannelGroup(groupId);
    }

    /**
     * Adds a given {@link Channel} to the {@link ChannelGroup} with the given id
     * @param channel   The {@link Channel} to add.
     * @param groupId   The id of the {@link ChannelGroup} the {@link Channel} is to be added to
     */
    public static void addChannelToGroup(Channel channel, String  groupId) {
        state.addChannelToGroup(channel, groupId);
    }

    /**
     * Removes a given {@link Channel} from the {@link ChannelGroup} with the given id.
     * @param channel   The {@link Channel} to remove.
     * @param groupId   The id of the {@link ChannelGroup} the {@link Channel} is to be removed from
     */
    public static void removeChannelFromGroup(Channel channel, String groupId) {
        state.removeChannelFromGroup(channel, groupId);
    }

    public static Map<String, ChannelGroup> getGroups() {
        return state.getGroups();
    }

    public static ChannelGroup getChannelGroup(String id) {
        return state.getChannelGroup(id);
    }

    public static Channel getChannel(String groupId, String playerId) {
        return state.getChannel(groupId, playerId);
    }
}
