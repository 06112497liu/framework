package com.lwb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 用于指定枚举的value
 * 根据该value可以获得对应的枚举的表示, 默认为value
 * value常用语标识枚举的展示
 * 只支持一个value标识, 常用于字典值
 * </pre>
 * @author xiehai
 * @date 2018/05/30 10:51
 * @Copyright(c) tellyes tech. inc. co.,ltd
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    /**
     * 默认value字段名称
     */
    String DEFAULT_VALUE_NAME = "value";
}