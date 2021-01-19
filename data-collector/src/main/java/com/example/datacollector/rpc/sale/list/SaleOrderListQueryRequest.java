package com.example.datacollector.rpc.sale.list;


import com.example.datacollector.rpc.DefaultRequest;
import com.example.datacollector.rpc.DefaultRequestParam;
import com.example.datacollector.rpc.Util;
import com.example.datacollector.rpc.MethodName;

import java.util.List;

public class SaleOrderListQueryRequest extends DefaultRequest {

    private static final byte[] fixedBytes = {0x00, 0x00, 0x0b, -72, 0x00, 0x2d, -56, 0x52};

    private static final MethodName methodName=MethodName.SALE_ORDER_LIST;

    private final byte[] queryStr = {0x12, 0x00};

    private final RequestPage requestPage;

    public SaleOrderListQueryRequest(String userToken, List<DefaultRequestParam> params, RequestPage requestPage) {
        super(userToken, fixedBytes, methodName, params);
        this.requestPage = requestPage;
    }

    public byte[] encode() {
        byte[] superData = super.encode();
        byte[] pageableBytes = requestPage.encode();
        int dataLength = superData.length + queryStr.length + pageableBytes.length;
        byte[] result = new byte[4 + dataLength];
        int resultPos = 0;
        System.arraycopy(Util.intToByteArray(dataLength), 0, result, resultPos, 4);
        resultPos += 4;
        System.arraycopy(superData, 0, result, resultPos, superData.length);
        resultPos += superData.length;
        System.arraycopy(queryStr, 0, result, resultPos, queryStr.length);
        resultPos += queryStr.length;
        System.arraycopy(pageableBytes, 0, result, resultPos, pageableBytes.length);
        return result;
    }

}
