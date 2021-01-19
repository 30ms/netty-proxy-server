package com.example.datacollector.rpc;

import io.netty.buffer.ByteBuf;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataDecoder {

    public static  <D> D decode(ByteBuf byteBuf, Class<D> dClass) throws IllegalAccessException, InstantiationException {
        ObjectData annotationClazz = dClass.getAnnotation(ObjectData.class);
        if(annotationClazz == null) throw new RuntimeException("不是DataObject注释的类:" + dClass);

        D instance = dClass.newInstance();
        List<Field> fields = Arrays.stream(dClass.getDeclaredFields())
                .filter(field -> field.getAnnotation(ObjectDataProperty.class) != null)
                .collect(Collectors.toList());
        for (Field field : fields) {
            ObjectDataProperty objectDataProperty = field.getAnnotation(ObjectDataProperty.class);

            int index = objectDataProperty.index();
            IndexType indexType = objectDataProperty.indexType();
            DataType dataType = objectDataProperty.dataType();
            boolean required = objectDataProperty.required();

            if (!indexType.equals(IndexType.NONE)) {
                int currIndex;
                byteBuf.markReaderIndex();
                if (indexType.equals(IndexType.UNSIGNED_BYTE)) {
                    currIndex = byteBuf.readUnsignedByte();
                } else if (indexType.equals(IndexType.UNSIGNED_SHORT)) {
                    currIndex = byteBuf.readUnsignedShort();
                } else {
                    throw new RuntimeException("错误的定位类型,只能使用unsignedByte和unsignedShort作为定位类型");
                }
                if (required) {
                    if (currIndex != index) {
                        throw new RuntimeException("定位错误:" + currIndex);
                    }
                }else {
                    if (currIndex != index) {
                        byteBuf.resetReaderIndex();
                        continue;
                    }
                }
            }

            Object value;
            switch (dataType) {
                case BYTE:
                    value = byteBuf.readByte();
                    break;
                case UNSIGNED_BYTE:
                    value = byteBuf.readUnsignedByte();
                    break;
                case UNSIGNED_SHORT:
                    value = byteBuf.readUnsignedShort();
                    break;
                case UNSIGNED_INT_LE:
                    value = byteBuf.readUnsignedIntLE();
                    break;
                case DOUBLE_LE:
                    value = byteBuf.readDoubleLE();
                    break;
                case CHAR_SEQUENCE:
                    short charLength = byteBuf.readUnsignedByte();
                    value = byteBuf.readCharSequence(charLength, StandardCharsets.UTF_8);
                    break;
                case OBJECT:
                    value = decode(byteBuf, field.getType());
                    break;
                case OBJECT_ARRAY:
                    Type genericType = field.getGenericType();
                    if (genericType instanceof Class) {
                        Class<?> clazz = (Class<?>) genericType;
                        if (clazz.isArray()) {
                            Class<?> componentType = clazz.getComponentType();
                            List<Object> list = new ArrayList<>();

                            boolean hasNext = hasNext(byteBuf);
                            while (hasNext) {
                                int i = byteBuf.readUnsignedByte();
                                int elementDataLength = byteBuf.readUnsignedShortLE() - 384;
                                Object element = decode(byteBuf, componentType);
                                list.add(element);
                                hasNext = hasNext(byteBuf);
                            }
                            Object arrayObject = Array.newInstance(componentType, list.size());
                            for (int i = 0; i < list.size(); i++) {
                                Array.set(arrayObject, i, list.get(i));
                            }
                            value = arrayObject;
                        }else {
                            throw new RuntimeException("类型错误,不是数组类型:" + genericType);
                        }
                    }else {
                        throw new RuntimeException("类型错误:" + genericType);
                    }
                    break;
                default:
                    throw new RuntimeException("映射类型错误");
            }
            field.setAccessible(true);
            field.set(instance, value);
        }
        return instance;
    }

    private static boolean hasNext(ByteBuf byteBuf) {
        int nextIndex = byteBuf.getUnsignedByte(byteBuf.readerIndex());
        int nextDataLength = byteBuf.getUnsignedShortLE(byteBuf.readerIndex() + 1) - 384;
        return nextIndex == 0x0a && byteBuf.readableBytes() >= nextDataLength;
    }
}
