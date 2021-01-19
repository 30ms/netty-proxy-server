package com.example.datacollector.rpc;

import java.nio.charset.StandardCharsets;

public class MethodName {

    public static final MethodName SALE_ORDER_LIST = new MethodName("list");

    public static final MethodName SALE_ORDER_ITEM = new MethodName("item");

    public static final MethodName SALE_DETAIL_QUERY = new MethodName("xiaoshoumingxichaxun");

    private String value;

    public MethodName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public byte[] encode() {
        byte[] mNB = value.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[24];
        System.arraycopy(mNB, 0, result, 0, mNB.length);
        return result;
    }
}
