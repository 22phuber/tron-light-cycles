package ch.tron.game;

import ch.tron.game.controller.Lobby;
import ch.tron.game.model.GameMode;
import ch.tron.middleman.messagedto.InAppMessage;
import ch.tron.middleman.messagedto.backAndForth.CurrentPublicGamesRequest;
import ch.tron.middleman.messagedto.transporttogame.*;
import ch.tron.middleman.messagehandler.ToTransportMessageForwarder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Connects {@link ch.tron.game} to {@code ch.tron.middleman}.
 * Manages forwarding of {@link InAppMessage}.
 */
public class GameManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    private static final ToTransportMessageForwarder MESSAGE_FORWARDER = new ToTransportMessageForwarder();

    private static Map<String,Lobby> lobbies = new HashMap<>();

    public static ToTransportMessageForwarder getMessageForwarder() { return MESSAGE_FORWARDER; }

    /**
     * Handles incoming {@link InAppMessage} from {@code ch.tron.middleman}.
     *
     * @param msg   Message of type {@link InAppMessage}.
     */
    public static void handleInAppIncomingMessage(InAppMessage msg) {
        if (msg instanceof CurrentPublicGamesRequest) {
            JSONArray all = new JSONArray();
            JSONObject jo = new JSONObject()
                    .put("subject", "currentPublicGames")
                    .put("games", all);
            for (Map.Entry<String, Lobby> entry: lobbies.entrySet()) {
                Lobby lobby = entry.getValue();
                if (lobby.isVisibleToPublic()) {
                    JSONObject one = new JSONObject()
                            .put("id", lobby.getId())
                            .put("name", lobby.getName())
                            .put("playersJoined", lobby.getPlayersJoined())
                            .put("playersAllowed", lobby.getMaxPlayers())
                            .put("mode", lobby.getGameMode().getName());
                    all.put(one);
                }
            }
            ((CurrentPublicGamesRequest) msg).setPublicGames(jo);
            MESSAGE_FORWARDER.forwardMessage(msg);
        }
        else if (msg instanceof NewLobbyMessage) {

            String groupId = ((NewLobbyMessage) msg).getGroupId();

            lobbies.put(groupId, new Lobby(
                    groupId,
                    ((NewLobbyMessage) msg).getGroupName(),
                    ((NewLobbyMessage) msg).getHostId(),
                    GameMode.getGameModeByName(((NewLobbyMessage) msg).getMode()),
                    ((NewLobbyMessage) msg).getPlayersAllowed(),
                    ((NewLobbyMessage) msg).isVisibleToPublic()
            ));
            new Thread(lobbies.get(groupId)).start();
        }
        else if (msg instanceof JoinLobbyMessage) {

            String groupId = ((JoinLobbyMessage) msg).getGroupId();
            String playerId = ((JoinLobbyMessage) msg).getPlayerId();

            lobbies.get(groupId).addPlayer(playerId);
        }
        else if (msg instanceof PlayerUpdateMessage) {

            String groupId = ((PlayerUpdateMessage) msg).getGroupId();
            String playerId = ((PlayerUpdateMessage) msg).getPlayerId();

            lobbies.get(groupId).updatePlayer(playerId, ((PlayerUpdateMessage) msg).getKey());
        }
        else if (msg instanceof StartGameMessage) {
            lobbies.get(((StartGameMessage) msg).getGroupId()).play();
        }
        else {
            LOGGER.info("Message type {} not supported", msg.getClass());
        }
    }
}
