package ch.tron.transport.webserverconfig;

import ch.tron.transport.websocketcontroller.WebSocketController;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.UUID;

public class SocketInitializer {

    public static void init() {

        final String NETTY_HOST = "localhost";
        final int NETTY_PORT = 9000;

        final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        final ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketChannelInitializer())
                .bind(new InetSocketAddress(NETTY_HOST, NETTY_PORT))
                .syncUninterruptibly()
                .channel()
                .closeFuture()
                .syncUninterruptibly();

        eventLoopGroup.shutdownGracefully();
    }
}
