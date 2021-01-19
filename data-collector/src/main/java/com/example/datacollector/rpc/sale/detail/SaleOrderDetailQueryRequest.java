package com.example.datacollector.rpc.sale.detail;

import com.example.datacollector.rpc.DefaultRequest;
import com.example.datacollector.rpc.DefaultRequestParam;
import com.example.datacollector.rpc.MethodName;
import com.example.datacollector.rpc.Util;

import java.util.List;

public class SaleOrderDetailQueryRequest extends DefaultRequest {

    //0x00 0x00 0x0b 0xb8 0x00 0x2d 0xc9 0x19
    private static final byte[] fixedBytes = {0x00, 0x00, 0x0b, -72, 0x00, 0x2d, -55, 0x19};

    private static final MethodName methodName = MethodName.SALE_DETAIL_QUERY;

    public SaleOrderDetailQueryRequest(String userToken, List<DefaultRequestParam> params) {
        super(userToken, fixedBytes, methodName, params);
    }

    @Override
    public byte[] encode() {
        byte[] superData = super.encode();
        byte[] result = new byte[4 + superData.length];
        int pos = 0;
        System.arraycopy(Util.intToByteArray(superData.length), 0, result, pos, 4);
        pos += 4;
        System.arraycopy(superData, 0, result, pos, superData.length);
        return result;
    }
}
