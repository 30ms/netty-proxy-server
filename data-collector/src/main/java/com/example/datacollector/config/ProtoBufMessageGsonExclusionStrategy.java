package com.example.datacollector.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;

public class ProtoBufMessageGsonExclusionStrategy implements ExclusionStrategy {


    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        Class<?> declaringClass = f.getDeclaringClass();
        String fieldName = f.getName();
        if(Message.class.isAssignableFrom(declaringClass)){
            return fieldName.equals("unknownFields") |
                    fieldName.equals("memoizedIsInitialized") |
                    fieldName.equals("memoizedSize") |
                    fieldName.equals("memoizedHashCode");
        }
        if (MessageLite.class.isAssignableFrom(declaringClass)) {
            return fieldName.equals("memoizedHashCode");
        }
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
