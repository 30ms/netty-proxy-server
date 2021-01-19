package cc.leevi.common.socks5proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Slf4JLoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;;

public class RelayToTargetHandler extends ChannelInboundHandlerAdapter {
    private final Channel targetChannel;

    private final InternalLogger logger = Slf4JLoggerFactory.getInstance(RelayToTargetHandler.class);

    public RelayToTargetHandler(Channel targetChannel) {
        this.targetChannel = targetChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (targetChannel.isActive()) {
            Util.writeByteBufToFile((ByteBuf) msg, "C:\\Users\\Administrator\\Desktop\\报文\\"
                    + LocalDate.now()+"\\" + ctx.channel().id().asShortText()+"\\请求报文.txt",true);
            targetChannel.writeAndFlush(msg);
        } else {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (targetChannel.isActive()) {
            targetChannel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
