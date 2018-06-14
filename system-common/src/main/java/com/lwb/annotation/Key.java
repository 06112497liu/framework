package com.lwb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 用于指定枚举的key
 * 根据该key可以获得对应的枚举类型, 默认为key
 * key常用于标识枚举的唯一性
 * 只支持一个key标识
 * </pre>
 * @author xiehai
 * @date 2018/04/12 11:44
 * @Copyright(c) tellyes tech. inc. co.,ltd
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {
    /**
     * 默认key字段名称
     */
    String DEFAULT_KEY_NAME = "key";
}
