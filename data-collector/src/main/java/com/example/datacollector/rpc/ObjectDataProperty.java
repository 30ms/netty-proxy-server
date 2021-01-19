package com.example.datacollector.rpc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectDataProperty {
    IndexType indexType() default IndexType.NONE;

    int index() default -1;

    DataType dataType() default DataType.OBJECT;

    boolean required() default true;
}
