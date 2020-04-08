package ch.tron.transport.websocket.state;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds all client-server-connections in groups where
 * each group represents a specific game round.
 */
public class WebSocketState {

    private final Map<String, ChannelGroup> groups = new HashMap<>();

    /**
     * Creates a new {@link ChannelGroup} (group of client-server-connections)
     * @return  The id of the newly created {@link ChannelGroup} as a string
     */
    public String addChannelGroup(ChannelGroup group) {
        //final String id = UUID.randomUUID().toString();

        // This is temporary
        final String id = "defaultId";

        groups.put(id, group);

        return id;
    }

    /**
     * Removes the {@link ChannelGroup} (group of client-server-connections) with the given id
     * @param id   The id of the {@link ChannelGroup} to be deleted
     */
    public void removeChannelGroup(String id) {
        groups.remove(id);
    }

    /**
     * Adds a given {@link Channel} to the {@link ChannelGroup} with the given id
     * @param channel   The {@link Channel} to add.
     * @param groupId   The id of the {@link ChannelGroup} the {@link Channel} is to be added to
     */
    public void addChannelToGroup(Channel channel, String groupId) {

        ChannelGroup group = groups.get(groupId);

        group.add(channel);
    }

    /**
     * Removes a given {@link Channel} from the {@link ChannelGroup} with the given id.
     * @param channel   The {@link Channel} to remove.
     * @param groupId   The id of the {@link ChannelGroup} the {@link Channel} is to be removed from
     */
    public void removeChannelFromGroup(Channel channel, String groupId) {

        groups.get(groupId).remove(channel);
    }

    public Channel getChannel(String groupId, String playerId) {

        Channel channel = groups.get(groupId).stream()
                .filter(ch -> ch.id().asLongText().equals(playerId))
                .findFirst()
                .orElse(null);
        return channel;
    }

    public Map<String, ChannelGroup> getGroups() {
        return groups;
    }

}