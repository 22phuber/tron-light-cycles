package ch.tron.transport.websocket.controller;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebSocketConnectionController {

    private static final Map<String, ChannelGroup> groups = new HashMap<>();
    private static final Map<String, Channel> lonelyPlayers = new HashMap<>();

    /**
     * Creates a new {@link ChannelGroup} (group of client-server-connections)
     * @return  The id of the newly created {@link ChannelGroup} as a string
     */
    public static String newChannelGroup() {
        final String id = UUID.randomUUID().toString();
        groups.put(
                id, new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE)
        );
        return id;
    }

    /**
     * Removes the {@link ChannelGroup} (group of client-server-connections) with the given id
     * @param groupId   The id of the {@link ChannelGroup} to be deleted
     */
    public static boolean deleteChannelGroup(String groupId) {
        return groups.remove(groupId) != null;
    }

    /**
     * Puts a given players connection to a default connection pool.
     * @param channel   The {@link Channel} that represents a players
     *                  connection to the server.
     */
    public static void addChannelToLonelyGroup(Channel channel) {
        lonelyPlayers.put(channel.id().asLongText(), channel);
    }

    /**
     * Removes a given player from the default connection pool. To be
     * called when a player attends a group of players.
     * @param playerId  The id of the given player.
     */
    public static boolean removeChannelFromLonelyGroup(String playerId) {
        return lonelyPlayers.remove(playerId) != null;
    }

    /**
     * Adds a given {@link Channel} to the {@link ChannelGroup} with the given id
     * @param channel   The {@link Channel} to add.
     * @param groupId   The id of the {@link ChannelGroup} the {@link Channel} is to be added to
     */
    public static void addChannelToGroup(Channel channel, String  groupId) {
        groups.get(groupId).add(channel);
    }

    /**
     * Removes a given {@link Channel} from the {@link ChannelGroup} with the given id.
     * @param channel   The {@link Channel} to remove.
     * @param groupId   The id of the {@link ChannelGroup} the {@link Channel} is to be removed from
     */
    public static boolean removeChannelFromGroup(Channel channel, String groupId) {
        return groups.get(groupId).remove(channel);
    }

    public static ChannelGroup getChannelGroupById(String groupId) {
        return groups.get(groupId);
    }

    public static Channel getChannelFromGroup(String groupId, String playerId) {
        return groups.get(groupId).stream()
                .filter(ch -> ch.id().asLongText().equals(playerId))
                .findFirst()
                .orElse(null);
    }

    public static Channel getLonelyChannel(String playerId) {
        return lonelyPlayers.get(playerId);
    }

    public static String findGroupIdOfChannel(Channel channel) {
        return groups.entrySet().stream()
                .filter(entry -> entry.getValue().contains(channel))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
