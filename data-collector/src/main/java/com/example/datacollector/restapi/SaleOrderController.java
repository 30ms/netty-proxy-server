package com.example.datacollector.restapi;

import com.example.datacollector.rpc.*;
import com.example.datacollector.rpc.sale.detail.SaleOrderDetailQueryRequest;
import com.example.datacollector.rpc.sale.detail.SaleOrderDetailQueryResponse;
import com.example.datacollector.rpc.sale.list.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Promise;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("sales-order")
public class SaleOrderController {

    private EventLoopGroup eventExecutors;

    public SaleOrderController(EventLoopGroup eventExecutors) {
        this.eventExecutors = eventExecutors;
    }

    @GetMapping("list")
    public ResponseEntity<SaleOrderListQueryResponse> saleOrderList(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "gzStartDate", required = false) LocalDate startDate,
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "gzEndDate", required = false) LocalDate endDate,
                                                                    @RequestParam(value = "transactionTypeStr",required = false) String transactionTypeStr,
                                                                    @RequestParam(value = "pageSize", defaultValue = "50") int pageSize,
                                                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        List<DefaultRequestParam> paramList = new ArrayList<>();
        DefaultRequestParam param = DefaultRequestParam.of(DefaultRequestParamName.of("gz_ksrq"),DefaultRequestParamValue.of(startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        paramList.add(param);
        DefaultRequestParam param2 = DefaultRequestParam.of(DefaultRequestParamName.of("gz_jsrq"), DefaultRequestParamValue.of(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        paramList.add(param2);
        if (transactionTypeStr != null) {
            DefaultRequestParam param3 = DefaultRequestParam.of(DefaultRequestParamName.of("swlx"), DefaultRequestParamValue.of(transactionTypeStr));
            paramList.add(param3);
        }
        SaleOrderListQueryRequest request = new SaleOrderListQueryRequest("ed8b111d891e42d28d58652b410eaa63", paramList, RequestPage.of(pageSize,pageNum));

        return ResponseEntity.ok(request(SaleOrderListQueryResponse.class, request));
    }

    @GetMapping("detail")
    public ResponseEntity<Object> saleOrderDetail(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "gzStartDate", required = false) LocalDate startDate,
                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "gzEndDate", required = false) LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        List<DefaultRequestParam> params = new ArrayList<>();
        //网点编码
        DefaultRequestParam param = DefaultRequestParam.of(DefaultRequestParamName.of("wdbm"), DefaultRequestParamValue.of("574"));
        params.add(param);
        //审核状态 1-已审核
        DefaultRequestParam param2 = DefaultRequestParam.of(DefaultRequestParamName.of("locked"), DefaultRequestParamValue.of("1"));
        params.add(param2);
        //大类编码
        DefaultRequestParam param3 = DefaultRequestParam.of(DefaultRequestParamName.of("dlbm"), DefaultRequestParamValue.of(""));
        params.add(param3);
        //材质编码
        DefaultRequestParam param4 = DefaultRequestParam.of(DefaultRequestParamName.of("czbm"), DefaultRequestParamValue.of(""));
        params.add(param4);
        //品类编码
        DefaultRequestParam param5 = DefaultRequestParam.of(DefaultRequestParamName.of("plbm"), DefaultRequestParamValue.of(""));
        params.add(param5);
        //款式编码
        DefaultRequestParam param6 = DefaultRequestParam.of(DefaultRequestParamName.of("ksbm"), DefaultRequestParamValue.of(""));
        params.add(param6);
        //归帐开始日期
        DefaultRequestParam param7 = DefaultRequestParam.of(DefaultRequestParamName.of("gzksrq"), DefaultRequestParamValue.of(startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        params.add(param7);
        //归帐结束日期
        DefaultRequestParam param8 = DefaultRequestParam.of(DefaultRequestParamName.of("gzjsrq"), DefaultRequestParamValue.of(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        params.add(param8);
        //事务类型
        DefaultRequestParam param9 = DefaultRequestParam.of(DefaultRequestParamName.of("swlx"), DefaultRequestParamValue.of("正常零售"));
        params.add(param9);

        DefaultRequest request = new SaleOrderDetailQueryRequest("ed8b111d891e42d28d58652b410eaa63", params);

        return ResponseEntity.ok(request(SaleOrderDetailQueryResponse.class, request));
    }


    private <R> R request(Class<R> rClass,DefaultRequest request) {
        R response;
        Promise<R> promise = eventExecutors.next().newPromise();
        new Bootstrap()
                .group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new RequestAndResponseHandler<>(promise, rClass, request.encode()));
                    }
                })
                .connect("121.201.119.60", 9001);

        try {
            promise.await();
            response = promise.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("RPC调用异常");
        }
        return response;
    }
}
