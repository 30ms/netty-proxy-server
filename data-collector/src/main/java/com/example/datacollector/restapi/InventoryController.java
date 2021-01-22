package com.example.datacollector.restapi;

import com.example.datacollector.rpc.*;
import com.example.datacollector.rpc.protobuf.InventoryQueryResponseProto;
import com.example.datacollector.rpc.inventory.InventoryRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    private EventLoopGroup eventExecutors;

    @GetMapping("list")
    public ResponseEntity inventoryQuery() {
        List<DefaultRequestParam> paramList=new ArrayList<>();
        DefaultRequestParam param=DefaultRequestParam.of(DefaultRequestParamName.of("wdbm"),DefaultRequestParamValue.of("574"));
        paramList.add(param);
        DefaultRequestParam param2=DefaultRequestParam.of(DefaultRequestParamName.of("ckbm"),DefaultRequestParamValue.of(""));
        paramList.add(param2);
        DefaultRequestParam param3=DefaultRequestParam.of(DefaultRequestParamName.of("dlbm"),DefaultRequestParamValue.of(""));
        paramList.add(param3);
        DefaultRequestParam param4=DefaultRequestParam.of(DefaultRequestParamName.of("czbm"),DefaultRequestParamValue.of(""));
        paramList.add(param4);
        DefaultRequestParam param5=DefaultRequestParam.of(DefaultRequestParamName.of("plbm"),DefaultRequestParamValue.of(""));
        paramList.add(param5);
        DefaultRequestParam param6=DefaultRequestParam.of(DefaultRequestParamName.of("ksbm"),DefaultRequestParamValue.of(""));
        paramList.add(param6);
        DefaultRequestParam param7=DefaultRequestParam.of(DefaultRequestParamName.of("tm"), DefaultRequestParamValue.of(""));
        paramList.add(param7);
        DefaultRequestParam param8=DefaultRequestParam.of(DefaultRequestParamName.of("zshy"),DefaultRequestParamValue.of(""));
        paramList.add(param8);
        DefaultRequestParam param9=DefaultRequestParam.of(DefaultRequestParamName.of("jz1"),DefaultRequestParamValue.of("-99999999"));
        paramList.add(param9);
        DefaultRequestParam param10=DefaultRequestParam.of(DefaultRequestParamName.of("jz2"),DefaultRequestParamValue.of("99999999"));
        paramList.add(param10);
        DefaultRequestParam param11=DefaultRequestParam.of(DefaultRequestParamName.of("xsjg1"),DefaultRequestParamValue.of("-999999999"));
        paramList.add(param11);
        DefaultRequestParam param12=DefaultRequestParam.of(DefaultRequestParamName.of("xsjg2"),DefaultRequestParamValue.of("99999999"));
        paramList.add(param12);
        DefaultRequestParam param13=DefaultRequestParam.of(DefaultRequestParamName.of("gfdj1"),DefaultRequestParamValue.of("0"));
        paramList.add(param13);
        DefaultRequestParam param14=DefaultRequestParam.of(DefaultRequestParamName.of("gfdj2"),DefaultRequestParamValue.of("999999999"));
        paramList.add(param14);
        DefaultRequestParam param15=DefaultRequestParam.of(DefaultRequestParamName.of("kdzt"),DefaultRequestParamValue.of("未开单"));
        paramList.add(param15);
        DefaultRequestParam param16=DefaultRequestParam.of(DefaultRequestParamName.of("djgl"),DefaultRequestParamValue.of("Y"));
        paramList.add(param16);
        DefaultRequestParam param17=DefaultRequestParam.of(DefaultRequestParamName.of("jcm"),DefaultRequestParamValue.of(""));
        paramList.add(param17);
        DefaultRequestParam param18=DefaultRequestParam.of(DefaultRequestParamName.of("zszl1"),DefaultRequestParamValue.of("0"));
        paramList.add(param18);
        DefaultRequestParam param19=DefaultRequestParam.of(DefaultRequestParamName.of("zszl2"),DefaultRequestParamValue.of("99999999"));
        paramList.add(param19);
        DefaultRequestParam param20=DefaultRequestParam.of(DefaultRequestParamName.of("spfl"),DefaultRequestParamValue.of(""));
        paramList.add(param20);

        InventoryRequest request=new InventoryRequest("ff7a1b00d9bb40d2a0dfc4e035f23192", paramList);
        Message message = request(request, InventoryQueryResponseProto.InventoryQueryResponse.getDefaultInstance());
        return ResponseEntity.ok(message);
    }

    private Message request(DefaultRequest request, MessageLite messageLite) {
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
