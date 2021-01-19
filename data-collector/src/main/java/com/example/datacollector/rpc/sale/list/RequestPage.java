package com.example.datacollector.rpc.sale.list;

public class RequestPage {

    private int pageSize;

    private int pageNum;

    public RequestPage(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public static RequestPage of(int pageSize, int pageNum) {
        return new RequestPage(pageSize, pageNum);
    }

    public byte[] encode() {
        byte[] result = new byte[4];
        result[0] = 0x28;
        result[1] = (byte) pageSize;
        result[2] = 0x30;
        result[3] = (byte) pageNum;
        return result;
    }
}
