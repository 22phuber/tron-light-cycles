package ch.tron.game.controller;

import ch.tron.game.controller.BattleRoyal;
import ch.tron.game.controller.Classic;
import ch.tron.game.controller.Lobby;
import ch.tron.game.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Lobby Class
 */
class LobbyTest {

    private Lobby lobby;
    private Player host;
    private Player player;


    @BeforeEach
    public void initializeLobby(){
        host = new Player("hostId", "hostName", "rgb(0,0,0)");
        player = new Player("playerId", "playerName", "rgb(1,1,1)");
        lobby = new Lobby("lobbyId", "lobbyName", host.getName(), host.getId(), host.getColor(), "classic", 10, true);
    }

    @Test
    void play() {
        lobby.play(host.getId());
        assertEquals(true, lobby.isPlaying());
    }

    @Test
    void removePlayer() {
        lobby.addPlayer(player.getId(), player.getName(), player.getColor());
        assertEquals(2, lobby.getPlayersJoined());
        lobby.removePlayer(player.getId());
        assertEquals(1, lobby.getPlayersJoined());
    }

    @Test
    void addPlayerAndGetPlayersJoined() {
        lobby.addPlayer(player.getId(), player.getName(), player.getColor());
        assertEquals(2, lobby.getPlayersJoined());
    }

    @Test
    void getId() {
        assertEquals("lobbyId", lobby.getId());
    }

    @Test
    void getName() {
        assertEquals("lobbyName", lobby.getName());
    }

    @Test
    void getGameClassic() {
        Map<String, Player> players = new HashMap<>();
        players.put(host.getId(), host);
        players.put(player.getId(), player);
        Assertions.assertTrue(lobby.getGame() instanceof Classic);
    }

    @Test
    void getGameBattleRoyal() {
        lobby = new Lobby("lobbyId", "lobbyName", host.getName(), host.getId(), host.getColor(), "battleRoyale", 100, true);
        Map<String, Player> players = new HashMap<>();
        players.put(host.getId(), host);
        players.put(player.getId(), player);
        Assertions.assertTrue(lobby.getGame() instanceof BattleRoyal);
    }

    @Test
    void getMaxPlayers() {
        assertEquals(10, lobby.getMaxPlayers());
    }

    @Test
    void isPlayingFalse() {
        assertEquals(false, lobby.isPlaying());
    }

    @Test
    void isPlayingTrue() {
        lobby.play(host.getId());
        assertEquals(true, lobby.isPlaying());
    }

    @Test
    void getNumberOfRounds() {
        assertEquals(5, lobby.getNumberOfRounds());
    }

    @Test
    void getRoundsPlayed() {
        assertEquals(0, lobby.getRoundsPlayed());
    }

    @Test
    void isVisibleToPublic() {
        assertEquals(true, lobby.isVisibleToPublic());
    }
}