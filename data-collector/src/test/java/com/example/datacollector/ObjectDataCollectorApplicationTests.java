package com.example.datacollector;

import com.example.datacollector.rpc.*;
import com.example.datacollector.rpc.sale.list.RequestPage;
import com.example.datacollector.rpc.sale.list.SaleOrderListQueryRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class ObjectDataCollectorApplicationTests {

    @Autowired
    private EventLoopGroup eventExecutors;

    @Test
    void contextLoads() throws IOException, InterruptedException, ExecutionException {

        List<DefaultRequestParam> paramList = new ArrayList<>();
        DefaultRequestParam param = DefaultRequestParam.of(DefaultRequestParamName.of("gz_ksrq"), DefaultRequestParamValue.of(LocalDate.now().minusDays(10).format(DateTimeFormatter.ISO_LOCAL_DATE)));
        paramList.add(param);
        DefaultRequestParam param2 = DefaultRequestParam.of(DefaultRequestParamName.of("gz_jsrq"), DefaultRequestParamValue.of(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        paramList.add(param2);
        SaleOrderListQueryRequest request = new SaleOrderListQueryRequest("ff7a1b00d9bb40d2a0dfc4e035f23192", paramList, RequestPage.of(50, 1));

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
                                .addLast(new RequestAndResponseHandler(request))
                                .addLast(new ProtobufDecoder(SaleOrderListResponseProto.SaleOrderListResponse.getDefaultInstance()))
                                .addLast(new DecodePromiseHandler(promise));
                    }
                })
                .connect("121.201.119.60", 9001);
        promise.await();
        System.out.println(promise.get());
    }

}
