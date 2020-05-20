package ch.tron.transport.webserverconfig;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Opens a socket.
 */
public class SocketInitializer {

    /**
     * Initializes a {@code SocketInitializer} object.
     */
    public SocketInitializer() {}

    /**
     * Initializes a socket.
     */
    public static void init() {

        final String NETTY_HOST = "0.0.0.0";
        //final String NETTY_HOST = "localhost";
        final int NETTY_PORT = 9000;

        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        final ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketChannelInitializer())
                .bind(new InetSocketAddress(NETTY_HOST, NETTY_PORT))
                .syncUninterruptibly()
                .channel()
                .closeFuture()
                .syncUninterruptibly();

        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
