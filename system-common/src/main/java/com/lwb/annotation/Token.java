package com.lwb.annotation;

import java.lang.annotation.*;

/**
 * 防止表单重复提交的注解
 *
 * @author liuweibo
 * @date 2018/6/22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Token {
    /**
     * 生成token标识
     *
     * @return
     */
    boolean save() default false;

    /**
     * 移除token标识
     *
     * @return
     */
    boolean remove() default false;
}
