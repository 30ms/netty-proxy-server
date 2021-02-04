package com.example.datacollector.restapi;

import com.example.datacollector.config.RPCRequestTemplate;
import com.example.datacollector.config.UserTokenStorage;
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

    @Autowired
    private UserTokenStorage userTokenStorage;

    @GetMapping("list")
    public ResponseEntity saleOrderList(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "ywStartDate", required = false) LocalDate ywStartDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "ywEndDate", required = false) LocalDate ywEndDate,
            @RequestParam(value = "transactionTypeStr", defaultValue = "") String transactionTypeStr,
            @RequestParam(value = "netPointNumber",defaultValue = "") String netPointNumber,
            @RequestParam(value = "pageSize", defaultValue = "50") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
//        if (gzStartDate == null) {
//            gzStartDate = LocalDate.now();
//        }
//        if (gzEndDate == null) {
//            gzEndDate = LocalDate.now();
//        }
        if (ywStartDate == null) {
            ywStartDate = LocalDate.now();
        }
        if (ywEndDate == null) {
            ywEndDate = LocalDate.now();
        }
        DefaultRequest request = new SaleOrderListQueryRequest(
                userTokenStorage.get(),
                SaleOrderListRequestProto.SaleOrderListRequestMessage.newBuilder()
//                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gz_ksrq").setValue(gzStartDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())
//                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gz_jsrq").setValue(gzEndDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("yw_ksrq").setValue(ywStartDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("yw_jsrq").setValue(ywEndDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("swlx").setValue(transactionTypeStr).build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("wdbm").setValue(netPointNumber).build())
//                        .setQueryConditionStr("")
                        .setPagSize(pageSize)
                        .setPageNum(pageNum)
                        .build());
        Message message = rpcRequestTemplate.call(request, SaleOrderListResponseProto.SaleOrderListResponseMessage.getDefaultInstance());
        return ResponseEntity.ok(message);
    }

    @GetMapping("detail")
    public ResponseEntity saleOrderDetail(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "ywStartDate", required = false) LocalDate startDate,
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "ywEndDate", required = false) LocalDate endDate,
                                          @RequestParam(value = "netPointNumber",defaultValue = "574,701,702,759,930") String netPointNumber,
                                          @RequestParam(value = "transactionTypeStr", defaultValue = "") String transactionTypeStr) {
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        DefaultRequest request = new SalesDetailQueryRequest(
                userTokenStorage.get(),
                SalesDetailQueryRequestProto.SalesDetailQueryRequestMessage.newBuilder()
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("wdbm").setValue(netPointNumber).build())     //网点编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("locked").setValue("1").build())       //审核状态 1-已审核
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("dlbm").setValue("").build())        //大类编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("czbm").setValue("").build())        //材质编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("plbm").setValue("").build())        //品类编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("ksbm").setValue("").build())        //款式编码
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("ywksrq").setValue(startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())        //归帐开始日期
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("ywjsrq").setValue(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).build())        //归帐结束日期
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("swlx").setValue(transactionTypeStr).build())        //事务类型
                        .build());

        Message message = rpcRequestTemplate.call(request, SalesDetailQueryResponseProto.SalesDetailQueryResponseMessage.getDefaultInstance());
        return ResponseEntity.ok(message);
    }
}
