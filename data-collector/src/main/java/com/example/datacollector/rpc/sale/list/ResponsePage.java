package com.example.datacollector.rpc.sale.list;

import com.example.datacollector.rpc.*;
import lombok.Getter;

@Getter
@ObjectData
public class ResponsePage {
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x10, dataType = DataType.UNSIGNED_BYTE)
   private int pageNum;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x18, dataType = DataType.UNSIGNED_BYTE)
   private int totalCount;

}

