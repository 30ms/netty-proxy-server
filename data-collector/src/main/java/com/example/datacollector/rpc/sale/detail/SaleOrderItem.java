package com.example.datacollector.rpc.sale.detail;


import com.example.datacollector.rpc.IndexType;
import com.example.datacollector.rpc.ObjectDataProperty;
import com.example.datacollector.rpc.ObjectData;
import com.example.datacollector.rpc.DataType;
import lombok.Getter;

@ObjectData
@Getter
public class SaleOrderItem {
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x0a,dataType = DataType.CHAR_SEQUENCE)
    private String netPointName;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x12,dataType = DataType.CHAR_SEQUENCE)
    private String unknownDateStr;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x1a,dataType = DataType.CHAR_SEQUENCE)
    private String unknownDateStr_2;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x22,dataType = DataType.CHAR_SEQUENCE)
    private String orderId;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x2a,dataType = DataType.CHAR_SEQUENCE)
    private String transactionType;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x32,dataType = DataType.CHAR_SEQUENCE)
    private String direction;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x3a,dataType = DataType.CHAR_SEQUENCE)
    private String serialNumber;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x42,dataType = DataType.CHAR_SEQUENCE)
    private String bigName;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x4a,dataType = DataType.CHAR_SEQUENCE)
    private String materialName;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x52,dataType = DataType.CHAR_SEQUENCE)
    private String categoryName;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x5a,dataType = DataType.CHAR_SEQUENCE)
    private String styleName;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x62,dataType = DataType.CHAR_SEQUENCE)
    private String productName;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x6a,dataType = DataType.CHAR_SEQUENCE)
    private String certificateNumber;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x72,dataType = DataType.CHAR_SEQUENCE)
    private String rawNumber;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_BYTE, index = 0x7a,dataType = DataType.CHAR_SEQUENCE)
    private String specification;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x8101,dataType = DataType.DOUBLE_LE)
    private double totalWeight;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x8901,dataType = DataType.DOUBLE_LE)
    private double goldWeight;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x9101,dataType = DataType.DOUBLE_LE,required = false)
    private double unknownDouble_1;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x9901,dataType = DataType.DOUBLE_LE,required = false)
    private double mainStoneWeight;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xa001,dataType = DataType.UNSIGNED_BYTE,required = false)
    private int mainStoneCount;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xaa01,dataType = DataType.CHAR_SEQUENCE)
    private String cleaness;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xb201,dataType = DataType.CHAR_SEQUENCE)
    private String color;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xb901,dataType = DataType.DOUBLE_LE,required = false)
    private double viceStoneWeight;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xc001,dataType = DataType.UNSIGNED_BYTE,required = false)
    private int viceStoneCount;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xc901,dataType = DataType.DOUBLE_LE)
    private double sellingDiscount;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xd101,dataType = DataType.DOUBLE_LE)
    private double goldPrice;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xd901,dataType = DataType.DOUBLE_LE,required = false)
    private double unknownDouble_2;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xe901,dataType = DataType.DOUBLE_LE,required = false)
    private double unKnownDouble_3;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xf101,dataType = DataType.DOUBLE_LE)
    private double tagPrice;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xf901,dataType = DataType.DOUBLE_LE)
    private double actualSellingPrice;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x8202,dataType = DataType.CHAR_SEQUENCE)
    private String productName_2;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x8a02,dataType = DataType.CHAR_SEQUENCE)
    private String printDetail;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x9002,dataType = DataType.UNSIGNED_BYTE)
    private int printCount;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x9a02,dataType = DataType.CHAR_SEQUENCE)
    private String storageDateStr;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xa202,dataType = DataType.CHAR_SEQUENCE)
    private String oneLevelSeries;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xaa02,dataType = DataType.CHAR_SEQUENCE)
    private String factoryNumber;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xb202,dataType = DataType.CHAR_SEQUENCE)
    private String unknownStr;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xba02,dataType = DataType.CHAR_SEQUENCE)
    private String warehouseName;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xc102,dataType = DataType.DOUBLE_LE)
    private double cashierAmount;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xc802,dataType = DataType.BYTE)
    private int count;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xd202,dataType = DataType.CHAR_SEQUENCE)
    private String cashierType;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xda02,dataType = DataType.CHAR_SEQUENCE)
    private String exchangeMaterialType;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xf102,dataType = DataType.DOUBLE_LE)
    private double unknownDouble_4;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0xfa02,dataType = DataType.CHAR_SEQUENCE)
    private String unknownStr_2;
    @ObjectDataProperty(indexType = IndexType.UNSIGNED_SHORT, index = 0x8203,dataType = DataType.CHAR_SEQUENCE)
    private String unit;
}
