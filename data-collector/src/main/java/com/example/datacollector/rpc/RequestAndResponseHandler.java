package com.example.datacollector.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Promise;

import java.util.HashMap;
import java.util.Map;

public class RequestAndResponseHandler<R> extends ChannelInboundHandlerAdapter {

    private static final Map<String, ByteBuf> channelDataBuf = new HashMap<>();
    private final Promise<R> responsePromise;
    private final byte[] requestData;
    private final Class<R> rClass;

    public RequestAndResponseHandler(Promise<R> responsePromise, Class<R> rClass, byte[] requestData) {
        this.responsePromise = responsePromise;
        this.requestData = requestData;
        this.rClass = rClass;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String id = ctx.channel().id().asShortText();
        ByteBuf byteBuf = channelDataBuf.get(id);
        byteBuf.markReaderIndex();
        int responseLength = byteBuf.readInt();
        int currentLength= byteBuf.writerIndex()-4;
        if (currentLength == responseLength) {
            byteBuf.skipBytes(12);//跳过12个空字节
            System.out.println("响应数据接收完成,准备断开连接...");
            ctx.channel().close();
        }else {
            byteBuf.resetReaderIndex();
        }
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        System.out.println("发送请求数据...");
        byteBuf.writeBytes(requestData);
        Util.writeByteBufToFile(byteBuf, "C:\\Users\\Administrator\\Desktop\\1.txt", false);
        ctx.writeAndFlush(byteBuf, ctx.newPromise().addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("发送请求数据成功");
            } else {
                throw new Exception("发送请求数据失败");
            }
        }));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String id = ctx.channel().id().asShortText();
        if (channelDataBuf.containsKey(id)) {
            ByteBuf byteBuf = channelDataBuf.get(id);
            Util.writeByteBufToFile(byteBuf, "C:\\Users\\Administrator\\Desktop\\2.txt", false);
            //解码响应数据
            R response = DataDecoder.decode(byteBuf, rClass);
            //返回异步数据
            responsePromise.setSuccess(response);
            byteBuf.release();
            channelDataBuf.remove(id);
        }
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String id = ctx.channel().id().asShortText();
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("channel:" + id + " 响应数据大小:" + byteBuf.readableBytes() + "B");
        if (!channelDataBuf.containsKey(id)) {
            channelDataBuf.put(id, Unpooled.buffer());
        }
        ByteBuf channelResponseDataBuf= channelDataBuf.get(id).writeBytes(byteBuf);
        System.out.println("channel:" + id + " 响应数据缓存区已写入大小:" + channelResponseDataBuf.writerIndex() +"B");
        super.channelRead(ctx, byteBuf);
    }
}
