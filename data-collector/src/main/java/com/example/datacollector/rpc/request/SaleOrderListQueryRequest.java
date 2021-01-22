package com.example.datacollector.rpc.request;


import com.example.datacollector.rpc.*;
import com.example.datacollector.rpc.protobuf.SaleOrderListRequestProto;

public class SaleOrderListQueryRequest extends DefaultRequest {

    private static final byte[] fixedBytes = {0x00, 0x00, 0x0b, -72, 0x00, 0x2d, -56, 0x52};

    private static final MethodName methodName=MethodName.SALE_ORDER_LIST;

    public SaleOrderListQueryRequest(UserToken userToken, SaleOrderListRequestProto.SaleOrderListRequestMessage requestMessage) {
        super(userToken, fixedBytes, methodName, requestMessage);
    }
}
