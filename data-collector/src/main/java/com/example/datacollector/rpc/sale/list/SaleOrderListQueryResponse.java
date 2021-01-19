package com.example.datacollector.rpc.sale.list;

import com.example.datacollector.rpc.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@ObjectData
@NoArgsConstructor
public class SaleOrderListQueryResponse {

    @ObjectDataProperty(dataType = DataType.OBJECT_ARRAY)
    private SaleOrder[] saleOrders;
    @ObjectDataProperty(dataType = DataType.OBJECT)
    private ResponsePage responsePage;
}
