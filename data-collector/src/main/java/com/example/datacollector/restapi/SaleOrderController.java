package com.example.datacollector.restapi;

import com.example.datacollector.config.RPCRequestTemplate;
import com.example.datacollector.rpc.*;
import com.example.datacollector.rpc.protobuf.*;
import com.example.datacollector.rpc.request.SaleOrderListQueryRequest;
import com.example.datacollector.rpc.request.SalesDetailQueryRequest;
import com.google.protobuf.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("sales-order")
public class SaleOrderController {

    @Autowired
    private RPCRequestTemplate rpcRequestTemplate;

    @GetMapping("list")
    public ResponseEntity saleOrderList(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "gzStartDate", required = false) LocalDate startDate,
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
        DefaultRequest request = new SaleOrderListQueryRequest(
                new UserToken("7fb14db3bbb8422ba874434f1eb34b5c"),
                SaleOrderListRequestProto.SaleOrderListRequestMessage.newBuilder()
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gz_ksrq").setValue(startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gz_jsrq").setValue(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())
//                        .setQueryConditionStr("")
                        .setPagSize(50)
                        .setPageNum(1)
                        .build());
        Message message = rpcRequestTemplate.call(request, SaleOrderListResponseProto.SaleOrderListResponseMessage.getDefaultInstance());
        return ResponseEntity.ok(message);
    }

    @GetMapping("detail")
    public ResponseEntity saleOrderDetail(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "gzStartDate", required = false) LocalDate startDate,
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "gzEndDate", required = false) LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        DefaultRequest request = new SalesDetailQueryRequest(
                new UserToken("7fb14db3bbb8422ba874434f1eb34b5c"),
                SalesDetailQueryRequestProto.SalesDetailQueryRequestMessage.newBuilder()
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("wdbm").setValue("574,701,702,759,930").build())     //网点编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("locked").setValue("1").build())       //审核状态 1-已审核
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("dlbm").setValue("").build())        //大类编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("czbm").setValue("").build())        //材质编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("plbm").setValue("").build())        //品类编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("ksbm").setValue("").build())        //款式编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gzksrq").setValue(startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())        //归帐开始日期
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gzjsrq").setValue(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())        //归帐结束日期
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("swlx").setValue("").build())        //事务类型
                        .build());

        Message message = rpcRequestTemplate.call(request, SalesDetailQueryResponseProto.SalesDetailQueryResponseMessage.getDefaultInstance());
        return ResponseEntity.ok(message);
    }
}
