package ch.tron.transport.websocketstate;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.HashMap;
import java.util.Map;

public class WebSocketState {

    private final Map<String, ChannelGroup> groups = new HashMap<>();

    public String addChannelGroup(ChannelGroup group) {
        //final String id = UUID.randomUUID().toString();

        // This is temporary
        final String id = "defaultId";

        groups.put(id, group);

        System.out.println("WebSocketState: ChannelGroup with id " + id + " has been added");

        return id;
    }


    public void removeChannelGroup(String id) {
        groups.remove(id);
    }

    public Map<String, ChannelGroup> getGroups() {
        return groups;
    }

    public void addChannelToGroup(Channel channel, String groupId) {

        ChannelGroup group = groups.get(groupId);

        System.out.println(group);

        group.add(channel);

        System.out.println(group);
    }

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

}
