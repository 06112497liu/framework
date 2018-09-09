package com.lwb.utils;

import com.google.common.collect.Lists;
import com.lwb.entity.exeception.UtilException;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 反射工具类
 *
 * @author liuweibo
 * @date 2018/8/23
 */
public interface ReflectUtil {

    /**
     * 获取某个类的所有字段（包括父类）
     * <note/>不包括static字段、{@link Transient}标记的字段和Object类中的字段
     *
     * @param clazz
     * @return
     */
    static List<Field> getFields(Class<?> clazz) {
        List<Field> result = Lists.newLinkedList();
        Class<?> tempClass = clazz;
        while (Objects.nonNull(tempClass) && !Objects.equals(tempClass, Object.class)) {
            List<Field> fields =
                Stream.of(tempClass.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .collect(Collectors.toList());
            result.addAll(fields);
            tempClass = tempClass.getSuperclass();
        }
        return result;
    }

    /**
     * 获取某个对象的某个字段的值
     *
     * @param field 字段
     * @param obj   对象
     * @return
     */
    static Object getFieldValue(Field field, Object obj) {
        try {
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }
            Object o = field.get(obj);
            if (accessible) {
                field.setAccessible(false);
            }
            return o;
        } catch (IllegalAccessException e) {
            throw new UtilException(e.getMessage());
        }

    }

    /**
     * 给某个字段赋值
     *
     * @param field 字段
     * @param obj   目标对象
     * @param value 值
     */
    static void setFieldValue(Field field, Object obj, String value) {
        try {
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }
            field.set(obj, value);
            if (accessible) {
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            throw new UtilException(e.getMessage());
        }
    }
}
