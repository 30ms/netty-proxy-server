package com.example.datacollector;

import com.example.datacollector.rpc.*;
import com.example.datacollector.rpc.protobuf.RequestParamProto;
import com.example.datacollector.rpc.protobuf.SaleOrderListRequestProto;
import com.example.datacollector.rpc.protobuf.SaleOrderListResponseProto;
import com.google.protobuf.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.util.concurrent.Promise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class ObjectDataCollectorApplicationTests {

    @Autowired
    private EventLoopGroup eventExecutors;

    @Test
    void contextLoads() throws IOException, InterruptedException, ExecutionException {

        TestDefaultRequest request = new TestProtobufRequest(
                new UserToken("7fb14db3bbb8422ba874434f1eb34b5c"),
                SaleOrderListRequestProto.SaleOrderListRequestMessage.newBuilder()
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gz_ksrq").setValue(LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE)).build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gz_jsrq").setValue(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)).build())
//                        .setQueryConditionStr("")
                        .setPagSize(50)
                        .setPageNum(1)
                        .build());

        Promise<Message> promise = eventExecutors.next().newPromise();

        new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new TestRequestAndResponseHandler(request))
                                .addLast(new ProtobufDecoder(SaleOrderListResponseProto.SaleOrderListResponseMessage.getDefaultInstance()))
                                .addLast(new DecodePromiseHandler(promise));
                    }
                })
                .connect("121.201.119.60", 9001);
        promise.await();
        System.out.println(promise.get());
    }

}
