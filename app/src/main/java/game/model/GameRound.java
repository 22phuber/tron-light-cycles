package game.model;

import game.config.CanvasConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class GameRound {

    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final Map<ChannelId, Player> players;

    public GameRound() {
        players = new HashMap<>();
    }


    public void addPlayer(Channel channel, Player pl) {
        channelGroup.add(channel);
        channel.writeAndFlush(new TextWebSocketFrame(CanvasConfig.getCanvasConfig().toString()));

        players.put(pl.getId(), pl);
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
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

}
