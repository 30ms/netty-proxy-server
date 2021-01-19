package com.example.datacollector.rpc.sale.detail;

import com.example.datacollector.rpc.DataType;
import com.example.datacollector.rpc.ObjectData;
import com.example.datacollector.rpc.ObjectDataProperty;
import lombok.Getter;

@ObjectData
@Getter
public class SaleOrderDetailQueryResponse {

    @ObjectDataProperty(dataType = DataType.OBJECT_ARRAY)
    private SaleOrderItem[] saleOrderItems;
}
