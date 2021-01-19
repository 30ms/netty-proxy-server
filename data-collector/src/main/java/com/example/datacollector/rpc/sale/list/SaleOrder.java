package com.example.datacollector.rpc.sale.list;

import com.example.datacollector.rpc.*;
import lombok.Getter;

@Getter
@ObjectData
public class SaleOrder{

   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x08, dataType = DataType.UNSIGNED_INT_LE)
   private long lineNum;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x12, dataType = DataType.CHAR_SEQUENCE)
   private String redFlushOrderId;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x22, dataType = DataType.CHAR_SEQUENCE)
   private String orderId;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x2a, dataType = DataType.CHAR_SEQUENCE)
   private String gzDateStr;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x32, dataType = DataType.CHAR_SEQUENCE)
   private String transactionTypeStr;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x42, dataType = DataType.CHAR_SEQUENCE)
   private String netPointNameStr;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x4a, dataType = DataType.CHAR_SEQUENCE)
   private String sellTypeStr;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x52, dataType = DataType.CHAR_SEQUENCE)
   private String businessDateStr;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x62, dataType = DataType.CHAR_SEQUENCE)
   private String assistantStr;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x6a, dataType = DataType.CHAR_SEQUENCE)
   private String unKnownStr1;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x70, dataType = DataType.UNSIGNED_BYTE)
   private int count;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x79, dataType = DataType.DOUBLE_LE)
   private double goldTotalWeight;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x9101, dataType = DataType.DOUBLE_LE)
   private double actualTotalSellingPrice;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x9901, dataType = DataType.DOUBLE_LE)
   private double discountTotalAmount;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xa101, dataType = DataType.DOUBLE_LE)
   private double couponTotalAmount;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xa901, dataType = DataType.DOUBLE_LE)
   private double receivableTotalAmount;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xb101, dataType = DataType.DOUBLE_LE)
   private double cashierTotalAmount;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xba01, dataType = DataType.CHAR_SEQUENCE)
   private String note;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xf201, dataType = DataType.CHAR_SEQUENCE)
   private String printDetail;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xfa01, dataType = DataType.CHAR_SEQUENCE)
   private String materials;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xa202, dataType = DataType.CHAR_SEQUENCE)
   private String unknownStr2;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xe202, dataType = DataType.CHAR_SEQUENCE)
   private String creator;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xea02, dataType = DataType.CHAR_SEQUENCE)
   private String createDateTimeStr;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x8203, dataType = DataType.CHAR_SEQUENCE)
   private String modifyDateTimeStr;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x9a03, dataType = DataType.CHAR_SEQUENCE)
   private String verifyDateTimeStr;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xa003, dataType = DataType.UNSIGNED_BYTE)
   private int unknownBit1;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x8004, dataType = DataType.UNSIGNED_BYTE)
   private int unknownBit2;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xb204, dataType = DataType.UNSIGNED_BYTE)
   private int unknownBit3;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xba04, dataType = DataType.UNSIGNED_BYTE)
   private int unknownBit4;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xb805, dataType = DataType.UNSIGNED_BYTE)
   private int unknownBit5;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xc205, dataType = DataType.UNSIGNED_BYTE)
   private int unknownBit6;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xe105, dataType = DataType.DOUBLE_LE)
   private double unknownDouble1;
   @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xe905, dataType = DataType.DOUBLE_LE)
   private double unknownDouble2;
}
