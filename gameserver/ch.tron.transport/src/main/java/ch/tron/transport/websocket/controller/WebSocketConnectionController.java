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
     * Initializes a {@code WebSocketConnectionController} object.
     */
    public WebSocketConnectionController() {}

    /**
     * Creates a new {@link ChannelGroup} (group of client-server
     * connections).
     *
     * @return  The id of the newly created {@link ChannelGroup} as
     * a string
     */
    public static String newChannelGroup() {
        final String id = UUID.randomUUID().toString();
        groups.put(id, new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE)
        );
        return id;
    }

    /**
     * Removes the {@link ChannelGroup} (group of client-server
     * connections) with the given id.
     *
     * @param groupId   The id of the {@link ChannelGroup} to be
     *                  deleted
     */
    public static boolean deleteChannelGroup(String groupId) {
        return groups.remove(groupId) != null;
    }

    /**
     * Puts a given client-server connection into a default
     * connection pool.
     *
     * @param channel   The {@link Channel} that represents a
     *                  clients connection to the server
     */
    public static void addChannelToLonelyGroup(Channel channel) {
        lonelyPlayers.put(channel.id().asLongText(), channel);
    }

    /**
     * Removes a given client-server connection from the default
     * connection pool.
     *
     * @param playerId  The id of the given client-server
     *                  connection
     */
    public static boolean removeChannelFromLonelyGroup(String playerId) {
        return lonelyPlayers.remove(playerId) != null;
    }

    /**
     * Adds a given {@link Channel} to the {@link ChannelGroup}
     * with the given id.
     *
     * @param channel   The {@link Channel} to add
     * @param groupId   The id of the {@link ChannelGroup} the
     *                  {@link Channel} is to be added to
     */
    public static void addChannelToGroup(Channel channel, String  groupId) {
        groups.get(groupId).add(channel);
    }

    /**
     * Removes a given {@link Channel} object from the
     * {@link ChannelGroup} with the given id.
     *
     * @param channel   The {@link Channel} object to remove
     * @param groupId   The id of the {@link ChannelGroup} object
     *                  the {@link Channel} object is to be
     *                  removed from
     */
    public static boolean removeChannelFromGroup(Channel channel, String groupId) {
        return groups.get(groupId).remove(channel);
    }

    /**
     * Returns the {@link ChannelGroup} object with the given id.
     *
     * @param groupId   The id of the {@link ChannelGroup} object
     * @return          The {@link ChannelGroup} object with the
     *                  given id or {@code null} if group with
     *                  given id does not exist
     */
    public static ChannelGroup getChannelGroupById(String groupId) {
        return groups.get(groupId);
    }

    /**
     * Returns the {@link Channel} object from a given group of
     * clients matching the given client id and the given group id.
     *
     * @param groupId   The id identifying the group of clients the
     *                  client given client is attached to
     * @param clientId  The id identifying the client
     * @return          The {@link Channel} object representing the
     *                  client-server connection or {@code null} if
     *                  client with given id is not present in
     *                  given group
     */
    public static Channel getChannelFromGroup(String groupId, String clientId) {
        return groups.get(groupId).stream()
                .filter(ch -> ch.id().asLongText().equals(clientId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the {@link Channel} object representing a client-
     * server connection to a client that is not attached to any
     * specific group of clients.
     *
     * @param clientId  The id identifying the client
     * @return          The {@link Channel} object representing the
     *                  client-server connection or {@code null} if
     *                  client is either attached to a specific
     *                  group of clients or if given client-server
     *                  connection does not exist
     */
    public static Channel getLonelyChannel(String clientId) {
        return lonelyPlayers.get(clientId);
    }

    /**
     * Returns the id of the group a client represented by the
     * given {@link Channel} object is attached to.
     *
     * @param channel   The {@link Channel} object representing a
     *                  specific client-server connection
     * @return          The id identifying the group of clients the
     *                  client is attached to if the latter is in
     *                  fact the case otherwise {@code null}
     */
    public static String findGroupIdOfChannel(Channel channel) {
        return groups.entrySet().stream()
                .filter(entry -> entry.getValue().contains(channel))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
