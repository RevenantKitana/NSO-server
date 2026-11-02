package com.nsoz.v2.adapter.network;

import com.nsoz.v2.adapter.network.controller.AuthPacketController;
import com.nsoz.v2.adapter.network.controller.PacketRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty Inbound Handler for Server V2.
 * Manages session lifecycle and dispatches decoded V2Message frames to PacketRouter.
 */
public class V2ChannelHandler extends SimpleChannelInboundHandler<V2Message> {
    private static final Logger logger = LoggerFactory.getLogger(V2ChannelHandler.class);

    private final PacketRouter router = PacketRouter.getInstance();
    private final AuthPacketController authController = AuthPacketController.getInstance();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        V2Session session = new V2Session(ctx.channel());
        ctx.channel().attr(V2Session.SESSION_KEY).set(session);
        logger.info("New client connection established from: [{}] (Session ID: {})", session.getIpAddress(), session.getSessionId());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, V2Message msg) {
        V2Session session = ctx.channel().attr(V2Session.SESSION_KEY).get();
        if (session == null) {
            ctx.close();
            return;
        }

        // Before handshake completion, first incoming packet triggers handshake response
        if (!session.isSendKeyComplete()) {
            logger.info("First packet (cmd={}) received from session [{}], initiating XOR handshake", msg.getCommand(), session.getSessionId());
            authController.sendHandshake(session);
            return;
        }

        // Route authenticated/encrypted packet
        router.route(session, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        V2Session session = ctx.channel().attr(V2Session.SESSION_KEY).get();
        if (session != null) {
            com.nsoz.v2.domain.world.WorldManager.getInstance().leaveCurrentZone(session);
            logger.info("Client disconnected: [{}] (Session ID: {})", session.getIpAddress(), session.getSessionId());
        }
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        V2Session session = ctx.channel().attr(V2Session.SESSION_KEY).get();
        long sid = session != null ? session.getSessionId() : -1;
        logger.warn("Channel exception on session [{}]: {}", sid, cause.getMessage());
        ctx.close();
    }
}

