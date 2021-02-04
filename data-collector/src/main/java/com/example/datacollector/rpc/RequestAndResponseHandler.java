package com.example.datacollector.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

public class RequestAndResponseHandler extends ChannelInboundHandlerAdapter {

    private static final Map<String, ByteBuf> channelDataBuf = new HashMap<>();
    private final DefaultRequest defaultRequest;

    public RequestAndResponseHandler(DefaultRequest defaultRequest) {
        this.defaultRequest = defaultRequest;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String id = ctx.channel().id().asShortText();
        if (channelDataBuf.containsKey(id)) {
            ByteBuf byteBuf = channelDataBuf.get(id);
            Util.writeByteBufToFile(byteBuf, "C:\\Users\\Administrator\\Desktop\\2.txt", false);
            byteBuf.markReaderIndex();
            long responseLength = byteBuf.readUnsignedInt();
            int currentLength= byteBuf.writerIndex()-4;
            if (currentLength == responseLength) {
                byteBuf.skipBytes(12);//跳过12个空字节
                System.out.println("响应数据接收完成");
                ctx.fireChannelRead(byteBuf);
                channelDataBuf.remove(id);
            }else {
                byteBuf.resetReaderIndex();
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        System.out.println("发送请求数据...");
        byteBuf.writeBytes(defaultRequest.encode());
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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String id = ctx.channel().id().asShortText();
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("channel:" + id + " 响应数据大小:" + byteBuf.readableBytes() + "B");
        if (!channelDataBuf.containsKey(id)) {
            channelDataBuf.put(id, Unpooled.buffer());
        }
        ByteBuf channelResponseDataBuf= channelDataBuf.get(id).writeBytes(byteBuf);
        System.out.println("channel:" + id + " 响应数据缓存区已写入大小:" + channelResponseDataBuf.writerIndex() +"B");
    }
}
