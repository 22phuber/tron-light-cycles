package ch.tron.transport.websocketstate;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebSocketState {

    private final Map<UUID, ChannelGroup> groups = new HashMap<>();

    public UUID addChannelGroup(ChannelGroup group) {
        final UUID id = UUID.randomUUID();
        groups.put(id, group);
        return id;
    }

    public void removeChannelGroup(UUID id) {
        groups.remove(id);
    }

    public void addChannelToGroup(Channel channel, UUID groupId) {
        groups.get(groupId).add(channel);
    }

    public void removeChannelFromGroup(Channel channel, UUID groupId) {
        groups.get(groupId).remove(channel);
    }

}
