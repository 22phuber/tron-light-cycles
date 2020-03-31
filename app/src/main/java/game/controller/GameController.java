package game.controller;

import game.config.CanvasConfig;
import game.model.GameRound;
import game.model.Player;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.json.JSONObject;
import transport.handler.JSONOutboundHandler;

import java.awt.Color;

public class GameController {

    private final Color[] colors10;

    private GameRound round;
    private JSONOutboundHandler out;

    private Thread gameLoop;
    private final double FPS = 60;
    private final double LOOP_INTERVAL = 1000000000 / FPS;

    public GameController(int canvasWidth, int canvasHeight) {
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

        round = new GameRound();
        out = new JSONOutboundHandler();

        gameLoop = new Thread(this::run);
        gameLoop.start();
    }

    public void addPlayer(Channel channel) {

        int player_count = round.playersMap().size();

        Player pl = new Player(
                channel.id(),
                50*player_count,
                50*player_count,
                1,
                colors10[player_count % colors10.length]);

        out.sendConfig(channel);

        round.addPlayer(channel, pl);
    }

    public void updatePlayer(JSONObject jo, ChannelId id) {
        Player pl = round.playersMap().get(id);
        int pl_dir = pl.getDir();
        String key = jo.getString("key");
        // HTML5 canvas coordinate system default setting
        // referenced by dir:
        //            (dir = 3)
        //                |
        //                |
        // (dir = 2) ----------- x (dir = 0)
        //                |
        //                |
        //                y
        //            (dir = 1)
        switch (key) {
            case "ArrowLeft":
                pl_dir = (pl_dir == 0) ? 3 : --pl_dir;
                break;
            case "ArrowRight":
                pl_dir = (pl_dir == 3) ? 0 : ++pl_dir;
                break;
        }
        pl.setDir(pl_dir);
    }

    private void run() {
        System.out.println("Game Loop running");
        long t_before = System.nanoTime();
        while (true) {
            long t_current = System.nanoTime();
            long t_delta = t_current - t_before;
            if (t_delta >= LOOP_INTERVAL) {
                t_before = t_current;
                round.playersMap().values().forEach(player -> {
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
                out.sendUpdate(round);
            }
        }
    }
}
