package com.nsoz.v2.adapter.network;

import com.nsoz.v2.infrastructure.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * High-performance Netty 4.1 Server listening on port 14445 for Server V2.
 */
public class V2NettyServer {
    private static final Logger logger = LoggerFactory.getLogger(V2NettyServer.class);

    private final int port;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;

    public V2NettyServer(int port) {
        this.port = port;
    }

    public synchronized void start() throws InterruptedException {
        ServerConfig config = ServerConfig.getInstance();
        bossGroup = new NioEventLoopGroup(config.getBossThreads());
        workerGroup = new NioEventLoopGroup(config.getWorkerThreads());

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_RCVBUF, 65536)
                .childOption(ChannelOption.SO_SNDBUF, 65536)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("codec", new V2PacketCodec());
                        pipeline.addLast("handler", new V2ChannelHandler());
                    }
                });

        ChannelFuture future = bootstrap.bind(port).sync();
        serverChannel = future.channel();
        logger.info("==================================================================");
        logger.info(" [SERVER V2] Netty Socket Server successfully bound to port: {}", port);
        logger.info("==================================================================");
    }

    public synchronized void stop() {
        logger.info("Stopping Netty Socket Server on port {}...", port);
        if (serverChannel != null) {
            serverChannel.close();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        logger.info("Netty Socket Server on port {} shut down successfully.", port);
    }
}

