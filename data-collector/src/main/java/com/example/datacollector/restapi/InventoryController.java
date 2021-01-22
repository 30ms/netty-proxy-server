package com.example.datacollector.restapi;

import com.example.datacollector.config.RPCRequestTemplate;
import com.example.datacollector.rpc.*;
import com.example.datacollector.rpc.protobuf.InventoryQueryRequestProto;
import com.example.datacollector.rpc.protobuf.InventoryQueryResponseProto;
import com.example.datacollector.rpc.protobuf.RequestParamProto;
import com.example.datacollector.rpc.request.InventoryRequest;
import com.google.protobuf.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    private RPCRequestTemplate rpcRequestTemplate;

    @GetMapping("list")
    public ResponseEntity inventoryQuery() {
        DefaultRequest request = new InventoryRequest(
                new UserToken("7fb14db3bbb8422ba874434f1eb34b5c"),
                InventoryQueryRequestProto.InventoryQueryRequestMessage.newBuilder()
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("wdbm").setValue("574").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("ckbm").setValue("").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("dlbm").setValue("").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("czbm").setValue("").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("plbm").setValue("").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("ksbm").setValue("").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("tm").setValue("").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("zshy").setValue("").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("jz1").setValue("-99999999").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("jz2").setValue("99999999").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("xsjg1").setValue("-999999999").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("xsjg2").setValue("99999999").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gfdj1").setValue("0").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("gfdj2").setValue("999999999").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("kdzt").setValue("未开单").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("djgl").setValue("Y").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("jcm").setValue("").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("zszl1").setValue("0").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("zszl2").setValue("99999999").build())
                        .addRequestParam(RequestParamProto.RequestParamMessage.newBuilder().setName("spfl").setValue("").build())
                        .build());

        Message message = rpcRequestTemplate.call(request, InventoryQueryResponseProto.InventoryQueryResponseMessage.getDefaultInstance());
        return ResponseEntity.ok(message);
    }
}
