package com.lwb.utils;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liuweibo
 * @date 2018/8/23
 */
public interface ReflectUtil {

    static List<Field> getFields(Class<?> clazz) {
        List<Field> result = Lists.newLinkedList();
        Class<?> tempClass = clazz;
        while (Objects.nonNull(tempClass)) {
            List<Field> fields =
                Stream.of(tempClass.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toList());
            result.addAll(fields);
            tempClass = tempClass.getSuperclass();
        }
        return result;
    }
}
