package com.example.datacollector.rpc;

import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;

public class DecodePromiseHandler extends SimpleChannelInboundHandler<Message> {

    private final Promise<Message> promise;

    public DecodePromiseHandler(Promise<Message> promise) {
        this.promise = promise;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        promise.setSuccess(msg);
        System.out.println("解码完成");
    }
}
