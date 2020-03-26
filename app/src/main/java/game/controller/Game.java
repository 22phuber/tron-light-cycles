package game.controller;

import game.config.CanvasConfig;
import game.model.Player;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private final Color[] colors10;
    private final ChannelGroup channelGroup;

    private Map<ChannelId, Player> players;

    private Thread gameLoop;
    private final double FPS = 60;
    private final double LOOP_INTERVAL = 1000000000 / FPS;

    public Game(ChannelGroup channelGroup, int canvasWidth, int canvasHeight) {
        colors10 = new Color[10];
        colors10[0] = new Color(44,123,246);
        colors10[1] = new Color(155,86,163);
        colors10[2] = new Color(229,93,156);
        colors10[3] = new Color(237,95,93);
        colors10[4] = new Color(233,135,58);
        colors10[5] = new Color(243,185,75);
        colors10[6] = new Color(120,183,86);
        colors10[7] = new Color(140,140,140);
        colors10[8] = new Color(67,70,74);
        colors10[9] = new Color(28,29,30);

        this.channelGroup = channelGroup;
        this.players = new HashMap<>();

        gameLoop = new Thread(this::run);
        gameLoop.start();
    }

    public void addPlayer(ChannelId id, Player pl) {
        players.put(id, pl);
    }

    public void removePlayer(ChannelId id) {
        players.remove(id);
    }

    public Map<ChannelId, Player> playersMap() {
        return players;
    }

    public JSONObject playersJSON() throws JSONException {
        JSONObject update = new JSONObject();
        update.put("subject", "player update");
        JSONArray all = new JSONArray();
        players.values().forEach(player -> {
            JSONObject one = new JSONObject();
            try {
                one.put("id", player.getId().asLongText());
                one.put("posx", player.getPosx());
                one.put("posy", player.getPosy());
                one.put("dir", player.getDir());
                one.put("color", awtColorToString(player.getColor()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            all.put(one);
        });
        update.put("players", all);
        return update;
    }

    public Color[] getColors10() {
        return colors10;
    }

    private String awtColorToString(Color c) {
        StringBuilder sb = new StringBuilder();
        sb.append("rgb(")
                .append(c.getRed())
                .append(",")
                .append(c.getGreen())
                .append(",")
                .append(c.getBlue())
                .append(")");
        return sb.toString();
    }

    private void run() {
        System.out.println("Game Loop running");
        long t_before = System.nanoTime();
        while (true) {
            long t_current = System.nanoTime();
            long t_delta = t_current - t_before;
            if (t_delta >= LOOP_INTERVAL) {
                t_before = t_current;
                players.values().forEach(player -> {
                    switch (player.getDir()) {
                        case 0:
                            player.setPosx((player.getPosx() + 1) % CanvasConfig.WIDTH.value());
                            break;
                        case 1:
                            player.setPosy((player.getPosy() + 1) % CanvasConfig.HEIGHT.value());
                            break;
                        case 2:
                            int x = player.getPosx();
                            if (x == 0) { player.setPosx(CanvasConfig.WIDTH.value()); }
                            else { player.setPosx(x - 1); }
                            break;
                        case 3:
                            int y = player.getPosy();
                            if (y == 0) { player.setPosy(CanvasConfig.HEIGHT.value()); }
                            else { player.setPosy(y - 1); }
                            break;
                    }
                });
                try {
                    String update = playersJSON().toString();
                    channelGroup.writeAndFlush(new TextWebSocketFrame(update));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
