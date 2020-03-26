package gameserver;

import game.config.CanvasConfig;
import game.controller.Game;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;
import gameserver.config.GameServerInitializer;

import java.net.InetSocketAddress;

public class GameServer {

    public static void main(String[] args) {
        final String NETTY_HOST = "localhost";
        final int NETTY_PORT = 9000;
        final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
        final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        final Game game = new Game(channelGroup, CanvasConfig.WIDTH.value(), CanvasConfig.HEIGHT.value());

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new GameServerInitializer(channelGroup, game))
                .bind(new InetSocketAddress(NETTY_HOST, NETTY_PORT))
                .syncUninterruptibly()
                .channel()
                .closeFuture()
                .syncUninterruptibly();

        eventLoopGroup.shutdownGracefully();
    }

}
