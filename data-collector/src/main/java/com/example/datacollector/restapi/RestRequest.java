package com.example.datacollector.restapi;

import lombok.Data;

@Data
public class RestRequest {
    private String orderId;
    private String tel;
    private double orderTotalPrice;

    public RestRequest(String orderId, String tel, double orderTotalPrice) {
        this.orderId = orderId;
        this.tel = tel;
        this.orderTotalPrice = orderTotalPrice;
    }
}
