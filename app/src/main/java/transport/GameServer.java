package transport;

import game.config.CanvasConfig;
import game.controller.GameController;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import transport.config.GameServerInitializer;

import java.net.InetSocketAddress;

public class GameServer {

    public final static GameController GAME_CONTROLLER;

    static {
        GAME_CONTROLLER = new GameController(CanvasConfig.WIDTH.value(), CanvasConfig.HEIGHT.value());
    }

    public static void main(String[] args) {
        final String NETTY_HOST = "localhost";
        final int NETTY_PORT = 9000;

        final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();


        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new GameServerInitializer())
                .bind(new InetSocketAddress(NETTY_HOST, NETTY_PORT))
                .syncUninterruptibly()
                .channel()
                .closeFuture()
                .syncUninterruptibly();

        eventLoopGroup.shutdownGracefully();
    }

}
