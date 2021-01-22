package com.example.datacollector.rpc.request;

import com.example.datacollector.rpc.*;
import com.example.datacollector.rpc.protobuf.SalesDetailQueryRequestProto;

public class SalesDetailQueryRequest extends DefaultRequest {

    //0x00 0x00 0x0b 0xb8 0x00 0x2d 0xc9 0x19
    private static final byte[] fixedBytes = {0x00, 0x00, 0x0b, -72, 0x00, 0x2d, -55, 0x19};

    private static final MethodName methodName = MethodName.SALE_DETAIL_QUERY;
    public SalesDetailQueryRequest(UserToken userToken, SalesDetailQueryRequestProto.SalesDetailQueryRequestMessage requestMessage) {
        super(userToken, fixedBytes, methodName, requestMessage);
    }
}
