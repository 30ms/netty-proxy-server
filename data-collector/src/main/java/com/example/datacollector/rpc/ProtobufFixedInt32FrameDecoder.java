package com.example.datacollector.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

public class ProtobufFixedInt32FrameDecoder extends ByteToMessageDecoder {
    private final DefaultRequest defaultRequest;

    public ProtobufFixedInt32FrameDecoder(DefaultRequest defaultRequest) {
        this.defaultRequest = defaultRequest;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        int length = readRawFixedInt32(in);
        if (length < 0) {
            throw new CorruptedFrameException("negative length: " + length);
        }

        if (in.readableBytes() < length) {
            int read = (in.writerIndex() - 4);
            System.out.println("已读取数据大小:" + read + "B,剩余数据大小:" + (length - read)+"B");
            in.resetReaderIndex();
        } else {
            System.out.println("读取完成,总数据大小:" + length + "B,开始解码");
            in.skipBytes(12);//跳过12个空字节
//            Util.writeByteBufToFile(in, "C:\\Users\\Administrator\\Desktop\\2.txt", false);
            out.add(in.readRetainedSlice(length-12));
            ctx.channel().close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();
        System.out.println("发送请求数据...");
        byteBuf.writeBytes(defaultRequest.encode());
//        Util.writeByteBufToFile(byteBuf, "C:\\Users\\Administrator\\Desktop\\1.txt", false);
        ctx.writeAndFlush(byteBuf, ctx.newPromise().addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("发送请求数据成功");
            } else {
                throw new Exception("发送请求数据失败");
            }
        }));
    }

    private static int readRawFixedInt32(ByteBuf buffer) {
        if (!buffer.isReadable()) {
            return 0;
        }
        return (int)buffer.readUnsignedInt();
    }
}
