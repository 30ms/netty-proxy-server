package cc.leevi.common.socks5proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.time.LocalDate;

public class RelayToSourceHandler extends ChannelInboundHandlerAdapter {
    private final Channel sourceChannel;

    public RelayToSourceHandler(Channel sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (sourceChannel.isActive()) {
            Util.writeByteBufToFile((ByteBuf) msg,"C:\\Users\\Administrator\\Desktop\\报文\\"
                    + LocalDate.now()+"\\" +sourceChannel.id().asShortText()+"\\返回报文.txt",true);
            sourceChannel.writeAndFlush(msg);
        } else {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (sourceChannel.isActive()) {
            sourceChannel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
