package com.example.datacollector.rpc.request;

import com.example.datacollector.rpc.*;
import com.example.datacollector.rpc.protobuf.InventoryQueryRequestProto;

public class InventoryRequest extends DefaultRequest {

    private static final byte[] fixedBytes = {0x00, 0x00, 0x0b, -72, 0x00, 0x2d, -55, 0x19};

    private static final MethodName methodName = MethodName.Inventory_QUERY;


    public InventoryRequest(UserToken userToken, InventoryQueryRequestProto.InventoryQueryRequestMessage requestMessage) {
        super(userToken, fixedBytes, methodName, requestMessage);
    }
}
