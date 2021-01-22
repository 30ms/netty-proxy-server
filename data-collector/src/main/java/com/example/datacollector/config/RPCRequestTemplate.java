package com.example.datacollector.config;

import com.example.datacollector.rpc.DecodePromiseHandler;
import com.example.datacollector.rpc.DefaultRequest;
import com.example.datacollector.rpc.RequestAndResponseHandler;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.util.concurrent.Promise;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class RPCRequestTemplate {

    private final EventLoopGroup eventExecutors;

    public RPCRequestTemplate(EventLoopGroup eventExecutors) {
        this.eventExecutors = eventExecutors;
    }

    public Message call(DefaultRequest request, MessageLite messageLite) {
        Message message;
        Promise<Message> promise = eventExecutors.next().newPromise();
        new Bootstrap()
                .group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new RequestAndResponseHandler(request))
                                .addLast(new ProtobufDecoder(messageLite))
                                .addLast(new DecodePromiseHandler(promise));
                    }
                })
                .connect("121.201.119.60", 9001);

        try {
            promise.await();
            message = promise.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("RPC调用异常");
        }
        return message;
    }
}
